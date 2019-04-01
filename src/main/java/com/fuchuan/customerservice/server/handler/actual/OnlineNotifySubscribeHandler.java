package com.fuchuan.customerservice.server.handler.actual;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.alibaba.fastjson.JSONArray;
import com.fuchuan.customerservice.common.AccountBaseInfo;
import com.fuchuan.customerservice.common.Command;
import com.fuchuan.customerservice.common.ImPacket;
import com.fuchuan.customerservice.common.payload.OnlineNotifySubscribeReqPayload;
import com.fuchuan.customerservice.common.payload.OnlineOfflinePushPayload;
import com.fuchuan.customerservice.db.IDao;
import com.fuchuan.customerservice.kit.TioKit;
import com.fuchuan.customerservice.server.handler.*;
import com.jfinal.kit.Kv;
import io.reactivex.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.utils.lock.ReadLockHandler;
import org.tio.utils.lock.SetWithLock;
import org.tio.websocket.common.WsRequest;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@HandlerForCommand(Command.COMMAND_ONLINE_NOTIFY_SUBSCRIBE_REQ)
public class OnlineNotifySubscribeHandler extends BaseActualHandler {
  private static final Logger log = LoggerFactory.getLogger(OnlineNotifySubscribeHandler.class);

  private final ConcurrentHashMap<String, WhoSetAndSubscribed> idToWS = new ConcurrentHashMap<>();

  public OnlineNotifySubscribeHandler(IDao dao) {
    super(dao);
  }

  @Override
  public Object handle(ImPacket _packet, WsRequest req, ChannelContext ctx) throws Exception {
    ImPacket<OnlineNotifySubscribeReqPayload> packet =
        ImPacket.convert(_packet, OnlineNotifySubscribeReqPayload.class);

    AccountBaseInfo me = (AccountBaseInfo) ctx.getAttribute("account");

    JSONArray who = packet.getPayload().getWho();
    if (who == null || who.size() == 0) return null;

    WhoSetAndSubscribed ws =
        idToWS.compute(
            ctx.getId(),
            (id, _ws) -> {
              if (_ws == null) {
                _ws = new WhoSetAndSubscribed(new ConcurrentHashSet<>(), new AtomicBoolean(false));
              }
              return _ws;
            });

    ws.whoSet.addAll(who.toJavaList(String.class));

    log.info("{} subscribed {}", me.getNickName(), ws.whoSet);

    // if this context is unsubscribed
    if (!ws.subscribed.getAndSet(true)) {
      Kv packDisposable = Kv.create();
      Disposable disposable =
          C.bus.subscribe(
              kv -> {
                String type = (String) kv.get("type");
                if (type == null) return;
                // on handshaked
                if (type.equals("handshaked")) {
                  // somebody has online
                  ChannelContext context = (ChannelContext) kv.get("ctx");
                  AccountBaseInfo account = (AccountBaseInfo) context.getAttribute("account");

                  // if account is subscribed by me
                  if (ws.whoSet.contains(context.userid) && account.getId() != me.getId()) {
                    // push online notify to me
                    log.info(
                        "push [{}] online notify to [{}]", account.getNickName(), me.getNickName());
                    TioKit.sendWsByText(
                        ctx,
                        new ImPacket<OnlineOfflinePushPayload>()
                            .setCommand(Command.COMMAND_ONLINE_PUSH)
                            .setPayload(
                                new OnlineOfflinePushPayload()
                                    .setId(account.getId())
                                    .setNickName(account.getNickName())
                                    .setAvatar(account.getAvatar())));
                  }
                }
                // on close
                if (type.equals("close")) {
                  // if closed context is this context
                  if (ctx.equals(kv.get("ctx"))) {
                    packDisposable.<Disposable>getAs("disposable").dispose();
                    idToWS.remove(ctx.getId());
                  } else {
                    // somebody offline
                    ChannelContext context = (ChannelContext) kv.get("ctx");
                    AccountBaseInfo account = (AccountBaseInfo) context.getAttribute("account");

                    if (ws.whoSet.contains(context.userid)) {
                      log.info(
                          "push [{}] offline notify to [{}]",
                          account.getNickName(),
                          me.getNickName());
                      TioKit.sendWsByText(
                          ctx,
                          new ImPacket<OnlineOfflinePushPayload>()
                              .setCommand(Command.COMMAND_OFFLINE_PUSH)
                              .setPayload(
                                  new OnlineOfflinePushPayload()
                                      .setId(account.getId())
                                      .setNickName(account.getNickName())
                                      .setAvatar(account.getAvatar())));
                    }
                  }
                }
              });
      packDisposable.set("disposable", disposable);
    }

    // for each new subscribed accounts, push online notify when account is online now
    who.parallelStream()
        .filter(Objects::nonNull)
        .forEach(
            id -> {
              boolean isOnline = TioKit.isOnline(ctx, (String) id);
              if (isOnline) {
                SetWithLock<ChannelContext> contexts =
                    Tio.getChannelContextsByUserid(ctx.groupContext, (String) id);
                if (contexts != null)
                  contexts.handle(
                      (ReadLockHandler<Set<ChannelContext>>)
                          ctxSet -> {
                            if (ctxSet != null && ctxSet.size() > 0) {
                              for (ChannelContext context : ctxSet) {
                                if (id.equals(context.userid)) {
                                  AccountBaseInfo account =
                                      (AccountBaseInfo) context.getAttribute("account");
                                  log.info(
                                      "push [{}] online notify to [{}]",
                                      account.getNickName(),
                                      me.getNickName());
                                  TioKit.sendWsByText(
                                      ctx,
                                      new ImPacket<OnlineOfflinePushPayload>()
                                          .setCommand(Command.COMMAND_ONLINE_PUSH)
                                          .setPayload(
                                              new OnlineOfflinePushPayload()
                                                  .setId(account.getId())
                                                  .setNickName(account.getNickName())
                                                  .setAvatar(account.getAvatar())));
                                }
                              }
                            }
                          });
              }
            });

    return null;
  }

  private class WhoSetAndSubscribed {
    final ConcurrentHashSet<String> whoSet;
    final AtomicBoolean subscribed;

    private WhoSetAndSubscribed(ConcurrentHashSet<String> whoSet, AtomicBoolean subscribed) {
      this.whoSet = whoSet;
      this.subscribed = subscribed;
    }
  }
}
