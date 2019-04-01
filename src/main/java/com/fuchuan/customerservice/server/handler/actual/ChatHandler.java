package com.fuchuan.customerservice.server.handler.actual;

import com.alibaba.fastjson.JSON;
import com.fuchuan.customerservice.common.AccountBaseInfo;
import com.fuchuan.customerservice.common.Command;
import com.fuchuan.customerservice.common.ImPacket;
import com.fuchuan.customerservice.common.model.MessageDetailModel;
import com.fuchuan.customerservice.common.model.RoomInfoModel;
import com.fuchuan.customerservice.common.payload.ChatReqPayload;
import com.fuchuan.customerservice.common.payload.RemindPushPayload;
import com.fuchuan.customerservice.db.Db;
import com.fuchuan.customerservice.db.IDao;
import com.fuchuan.customerservice.kit.TioKit;
import com.fuchuan.customerservice.server.handler.BaseActualHandler;
import com.fuchuan.customerservice.server.handler.HandlerForCommand;
import com.jfinal.kit.Kv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.utils.lock.ReadLockHandler;
import org.tio.utils.lock.SetWithLock;
import org.tio.websocket.common.WsRequest;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@HandlerForCommand(Command.COMMAND_CHAT_REQ)
public class ChatHandler extends BaseActualHandler {
  private static final Logger log = LoggerFactory.getLogger(ChatHandler.class);

  public ChatHandler(IDao dao) {
    super(dao);
  }

  @Override
  public Object handle(ImPacket _packet, WsRequest req, ChannelContext ctx) {
    try {
      ImPacket<ChatReqPayload> packet = ImPacket.convert(_packet, ChatReqPayload.class);
      AccountBaseInfo me = (AccountBaseInfo) ctx.getAttribute("account");
      ChatReqPayload payload = packet.getPayload();
      // 一对一
      if (payload.getType() == 1) {
        String to = payload.getTo();
        // not self to self
        if (ctx.userid.equals(to)) return null;

        Kv ret = dao.sendChatMsg(payload, me.getId());

        MessageDetailModel msg = ret.getAs("msg");

        RoomInfoModel room = ret.getAs("room");

        // Maybe not good enough?
        //        // save message
        //        String roomKey = Db.K.roomKey(me.getId(), to);
        //        MessageDetailModel msg =
        //            new MessageDetailModel()
        //                .setMsgKey(dao.nextId())
        //                .setFrom(me.getId())
        //                .setTo(to)
        //                .setSendAt(new Date().getTime())
        //                .setContent(payload.getContent());
        //
        //        log.info("{} send msg to {}, msg = {}", me.getId(), to, JSON.toJSONString(msg));
        //
        //        dao.saveMessage(msg, roomKey);
        //
        //        dao.joinRoom(Kv.by(ctx.userid, roomKey).set(to, roomKey));
        //
        //        dao.saveRoomInfo(
        //            new RoomInfoModel().setType(1).setRoomKey(roomKey),
        //            roomKey,
        //            Stream.of(to, me.getId()).collect(Collectors.toSet()));
        //
        //        // add to remind
        //        dao.addRemind(msg.getMsgKey(), roomKey, to);

        SetWithLock<ChannelContext> toUserCtxSet =
            Tio.getChannelContextsByUserid(ctx.getGroupContext(), to);

        SetWithLock<ChannelContext> fromUserCtxSet =
            Tio.getChannelContextsByUserid(ctx.getGroupContext(), me.getId());

        if (toUserCtxSet == null) {
          toUserCtxSet = new SetWithLock<>(new HashSet<>());
        }

        toUserCtxSet.handle(
            (ReadLockHandler<Set<ChannelContext>>)
                ((ctxs) -> {
                  ctxs.addAll(fromUserCtxSet != null ? fromUserCtxSet.getObj() : new HashSet<>());
                  TioKit.sendWsToCtxSetByText(
                      ctxs,
                      new ImPacket<RemindPushPayload>()
                          .setCommand(Command.COMMAND_REMIND_PUSH)
                          .setPayload(
                              new RemindPushPayload()
                                  .setMsgKey(msg.getMsgKey())
                                  .setRoomKey(room.getRoomKey())
                                  .setFrom(msg.getFrom())
                                  .setTo(msg.getTo())
                                  .setContent(msg.getContent())
                                  .setSendAt(msg.getSendAt())));
                }));

      }
      // 群聊
      else if (payload.getType() == 2) {
        // TODO: 实现群聊
      }
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
