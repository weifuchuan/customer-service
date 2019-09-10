package com.fuchuan.customerservice.server.handler;

import com.fuchuan.customerservice.common.*;
import com.fuchuan.customerservice.common.payload.OnlineOfflineNotifyPayload;
import com.fuchuan.customerservice.common.payload.OnlineOfflinePushPayload;
import com.fuchuan.customerservice.db.IDao;
import com.fuchuan.customerservice.kit.TioKit;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.core.intf.Packet;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.utils.lock.ReadLockHandler;
import org.tio.utils.lock.SetWithLock;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MainHandler implements IWsMsgHandler {
  private static Logger log = LoggerFactory.getLogger(MainHandler.class);

  private AccountServiceGrpc.AccountServiceBlockingStub accountService;
  private IActualHandler realHandler;

  private IDao dao;

  public MainHandler(
      AccountServiceGrpc.AccountServiceBlockingStub accountService,
      IActualHandler realHandler,
      IDao dao) {
    this.accountService = accountService;
    this.realHandler = realHandler;
    this.dao = dao;
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
        C.bus.onNext(
            Collections.unmodifiableMap(
                Kv.create().set(Kv.by("type", "handshaked").set("ctxId", ctx.getId()))));
        dao.incrOnlineCount(ctx.userid);
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
    C.bus.onNext(
        Collections.unmodifiableMap(
            Kv.create().set(Kv.by("type", "close").set("ctxId", ctx.getId()))));
    dao.decrOnlineCount(ctx.userid);
    return null;
  }
}
