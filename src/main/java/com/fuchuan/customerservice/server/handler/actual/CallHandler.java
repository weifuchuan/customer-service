package com.fuchuan.customerservice.server.handler.actual;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fuchuan.customerservice.common.*;
import com.fuchuan.customerservice.common.model.MessageDetailModel;
import com.fuchuan.customerservice.common.model.RoomInfoModel;
import com.fuchuan.customerservice.common.payload.CallReqPayload;
import com.fuchuan.customerservice.common.payload.CallRespPayload;
import com.fuchuan.customerservice.db.Db;
import com.fuchuan.customerservice.db.IDao;
import com.fuchuan.customerservice.kit.TioKit;
import com.fuchuan.customerservice.server.handler.BaseActualHandler;
import com.fuchuan.customerservice.server.handler.HandlerForCommand;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.utils.lock.SetWithLock;
import org.tio.utils.page.Page;
import com.fuchuan.customerservice.server.websocket.common.WsRequest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 来自客户端的调用请求，相当于： <code>
 *   function [action](payload){
 *     // ...
 *     return ret
 *   }
 * </code> 客户端使用： <code>
 *   const ret = await call(action, payload);
 * </code>
 */
@HandlerForCommand(Command.COMMAND_CALL_REQ)
public class CallHandler extends BaseActualHandler {
  private static final Logger log = LoggerFactory.getLogger(CallHandler.class);

  private AccountServiceGrpc.AccountServiceBlockingStub stub =
      feather.instance(AccountServiceGrpc.AccountServiceBlockingStub.class);

  public CallHandler(IDao dao) {
    super(dao);
  }

  @Override
  public Object handle(ImPacket _packet, WsRequest req, ChannelContext ctx) throws Exception {
    ImPacket<CallReqPayload> packet = ImPacket.convert(_packet, CallReqPayload.class);
    Map ret =
        handle(
            packet.getPayload().getAction(),
            JSON.parseObject(packet.getPayload().getPayload()),
            req,
            ctx);
    TioKit.sendWsByText(
        ctx,
        new ImPacket<CallRespPayload>()
            .setCommand(Command.COMMAND_CALL_RESP)
            .setPayload(
                new CallRespPayload()
                    .setId(packet.getPayload().getId())
                    .setRet(JSON.toJSONString(ret))));
    return null;
  }

  private Map handle(String action, JSONObject payload, WsRequest req, ChannelContext ctx)
      throws Exception {
    //    log.info("action = {}, payload = {}", action, JSON.toJSONString(payload));
    switch (action) {
      case "fetchMyAccountBaseInfo":
        {
          AccountBaseInfo account = (AccountBaseInfo) ctx.getAttribute("account");
          return Kv.by("id", account.getId())
              .set("nickName", account.getNickName())
              .set("avatar", account.getAvatar())
              .set("isOnline", true);
        }
      case "fetchAccountBaseInfo":
        {
          AccountBaseInfoReply reply =
              stub.fetchBaseInfo(
                  AccountBaseInfoReq.newBuilder().setId(payload.getString("id")).build());
          if (reply.getCode() == Code.OK) {
            AccountBaseInfo account = reply.getAccount();
            return Ret.ok(
                "account",
                Kv.by("id", account.getId())
                    .set("nickName", account.getNickName())
                    .set("avatar", account.getAvatar())
                    .set("isOnline", TioKit.isOnline(ctx, account.getId())));
          } else {
            return Ret.fail();
          }
        }
      case "fetchJoinedRoomList":
        {
          List<Map> roomList = dao.joinedRoomList(ctx.userid);
          return Kv.by("roomList", roomList);
        }
      case "fetchRoomInfo":
        {
          String roomKey;
          if (payload.getIntValue("type") == 1) {
            roomKey = Db.K.roomKey(ctx.userid, payload.getString("to"));
          } else {
            roomKey = Db.K.roomKey(payload.getString("to"));
          }
          RoomInfoModel roomInfo = dao.getRoomInfo(roomKey, ctx.userid);
          return roomInfo;
        }
      case "fetchWaiters":
        {
          Waiters waiters = stub.fetchWaiters(WaitersReq.newBuilder().build());
          List<AccountBaseInfo> accountList = waiters.getAccountList();
          return Kv.by(
              "waiterList",
              accountList.stream()
                  .map(
                      account -> {
                        return Kv.by("id", account.getId())
                            .set("nickName", account.getNickName())
                            .set("avatar", account.getAvatar())
                            .set("isOnline", TioKit.isOnline(ctx, account.getId()));
                      })
                  .collect(Collectors.toList()));
        }
      case "fetchMessagePage":
        {
          String roomKey = payload.getString("roomKey");
          Integer pageNumber = payload.getInteger("pageNumber");
          Integer pageSize = payload.getInteger("pageSize");
          Page<MessageDetailModel> messagePage = dao.messagePage(roomKey, pageNumber, pageSize);
          return Kv.by("page", messagePage);
        }
      case "fetchAccountListBaseInfo":
        {
          JSONArray idList = payload.getJSONArray("idList");
          FetchAccountListBaseInfoReply reply =
              stub.fetchAccountListBaseInfo(
                  FetchAccountListBaseInfoReq.newBuilder()
                      .addAllId(idList.toJavaList(String.class))
                      .build());
          List<AccountBaseInfo> accountList = reply.getAccountList();
          return Kv.by(
              "accountList",
              accountList.stream()
                  .map(
                      account -> {
                        return Kv.by("id", account.getId())
                            .set("nickName", account.getNickName())
                            .set("avatar", account.getAvatar());
                      })
                  .toArray());
        }
    }
    return Kv.create();
  }
}
