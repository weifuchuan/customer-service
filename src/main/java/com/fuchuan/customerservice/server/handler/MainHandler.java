package com.fuchuan.customerservice.server.handler;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.fuchuan.customerservice.common.*;
import com.fuchuan.customerservice.common.payload.OnlineOfflinePushPayload;
import com.fuchuan.customerservice.kit.TioKit;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.utils.lock.ReadLockHandler;
import org.tio.utils.lock.SetWithLock;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.util.Collections;
import java.util.Set;

public class MainHandler implements IWsMsgHandler {
  private static Logger log = LoggerFactory.getLogger(MainHandler.class);

  private AccountServiceGrpc.AccountServiceBlockingStub accountService;
  private IActualHandler realHandler;

  public MainHandler(
      AccountServiceGrpc.AccountServiceBlockingStub accountService, IActualHandler realHandler) {
    this.accountService = accountService;
    this.realHandler = realHandler;
  }

  @Override
  public HttpResponse handshake(HttpRequest req, HttpResponse resp, ChannelContext ctx)
      throws Exception {
    String id = req.getParam("id");
    if (StrKit.isBlank(id)) {
      Tio.close(ctx, "id is blank");
      return null;
    }
    Tio.bindUser(ctx, id);
    return resp;
  }

  @Override
  public void onAfterHandshaked(HttpRequest req, HttpResponse resp, ChannelContext ctx)
      throws Exception {
    log.info("user {} connected", ctx.userid);
    try {
      AccountBaseInfoReply accountBaseInfoReply =
          accountService.fetchBaseInfo(AccountBaseInfoReq.newBuilder().setId(ctx.userid).build());
      if (accountBaseInfoReply.getCode() == Code.OK) {
        AccountBaseInfo account = accountBaseInfoReply.getAccount();
        ctx.setAttribute("account", account);

        //        if (accountBaseInfoReply.getRole() == Role.WAITER) {
        //          handleWaiterOnline(account, ctx);
        //        }

        C.bus.onNext(
            Collections.unmodifiableMap(
                Kv.create().set(Kv.by("type", "handshaked").set("ctx", ctx))));
      } else {
        Tio.close(ctx, "account not exists");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      Tio.close(ctx, "grpc service exception");
    }
  }

  @Override
  public Object onBytes(WsRequest req, byte[] bytes, ChannelContext ctx) throws Exception {
    ImPacket packet = ImPacketCoder.decode(bytes);
    return realHandler.handle(packet, req, ctx);
  }

  @Override
  public Object onText(WsRequest req, String text, ChannelContext ctx) throws Exception {
    ImPacket packet = ImPacketCoder.decodeFromString(text);
    return realHandler.handle(packet, req, ctx);
  }

  @Override
  public Object onClose(WsRequest req, byte[] bytes, ChannelContext ctx) throws Exception {
    log.info("user {} disconnected", ctx.userid);
    //    if (C.idToOnlineWaiterCtxSet.containsKey(ctx.userid)
    //        && !C.idToOnlineWaiterCtxSet.get(ctx.userid).isEmpty()
    //        && C.idToOnlineWaiterCtxSet.get(ctx.userid).contains(ctx)) {
    //      handleWaiterOffline(ctx);
    //    }

    C.bus.onNext(
        Collections.unmodifiableMap(Kv.create().set(Kv.by("type", "close").set("ctx", ctx))));
    return null;
  }

  @Deprecated
  private void handleWaiterOnline(AccountBaseInfo account, ChannelContext ctx) {
    C.idToOnlineWaiterCtxSet.compute(
        ctx.userid,
        (id, ctxSet) -> {
          if (ctxSet == null) {
            ctxSet = new ConcurrentHashSet<>();
          }
          if (ctxSet.isEmpty()) {
            SetWithLock<ChannelContext> allChannelContexts =
                Tio.getAllChannelContexts(ctx.getGroupContext());
            allChannelContexts.handle(
                (ReadLockHandler<Set<ChannelContext>>)
                    (ctxs) -> {
                      TioKit.sendWsToCtxSetByText(
                          ctxs,
                          new ImPacket<OnlineOfflinePushPayload>()
                              .setCommand(Command.COMMAND_ONLINE_PUSH)
                              .setPayload(
                                  new OnlineOfflinePushPayload()
                                      .setId(account.getId())
                                      .setNickName(account.getNickName())
                                      .setAvatar(account.getAvatar())));
                    });
          }
          ctxSet.add(ctx);
          return ctxSet;
        });
  }

  @Deprecated
  private void handleWaiterOffline(ChannelContext ctx) {
    C.idToOnlineWaiterCtxSet.compute(
        ctx.userid,
        (id, ctxSet) -> {
          if (ctxSet == null) {
            ctxSet = new ConcurrentHashSet<>();
          }
          ctxSet.remove(ctx);
          if (ctxSet.isEmpty()) {
            AccountBaseInfo account = (AccountBaseInfo) ctx.getAttribute("account");
            SetWithLock<ChannelContext> allChannelContexts =
                Tio.getAllChannelContexts(ctx.getGroupContext());
            allChannelContexts.handle(
                (ReadLockHandler<Set<ChannelContext>>)
                    (ctxs) -> {
                      TioKit.sendWsToCtxSetByText(
                          ctxs,
                          new ImPacket<OnlineOfflinePushPayload>()
                              .setCommand(Command.COMMAND_OFFLINE_PUSH)
                              .setPayload(
                                  new OnlineOfflinePushPayload()
                                      .setId(account.getId())
                                      .setNickName(account.getNickName())
                                      .setAvatar(account.getAvatar())));
                    });
          }
          return ctxSet;
        });
  }
}
