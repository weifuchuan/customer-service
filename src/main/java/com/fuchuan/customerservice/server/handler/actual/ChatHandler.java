package com.fuchuan.customerservice.server.handler.actual;

import com.fuchuan.customerservice.common.AccountBaseInfo;
import com.fuchuan.customerservice.common.Command;
import com.fuchuan.customerservice.common.ImPacket;
import com.fuchuan.customerservice.common.model.MessageDetailModel;
import com.fuchuan.customerservice.common.model.RoomInfoModel;
import com.fuchuan.customerservice.common.payload.ChatReqPayload;
import com.fuchuan.customerservice.common.payload.RemindPushPayload;
import com.fuchuan.customerservice.db.IDao;
import com.fuchuan.customerservice.kit.TioKit;
import com.fuchuan.customerservice.server.handler.BaseActualHandler;
import com.fuchuan.customerservice.server.handler.HandlerForCommand;
import org.tio.websocket.common.WsRequest;
import com.jfinal.kit.Kv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

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

        ImPacket<RemindPushPayload> remind =
          new ImPacket<RemindPushPayload>()
            .setCommand(Command.COMMAND_REMIND_PUSH)
            .setPayload(
              new RemindPushPayload()
                .setMsgKey(msg.getMsgKey())
                .setRoomKey(room.getRoomKey())
                .setFrom(msg.getFrom())
                .setTo(msg.getTo())
                .setContent(msg.getContent())
                .setSendAt(msg.getSendAt()));

        TioKit.sendWSToUserIdByText(ctx, me.getId(), remind);
        TioKit.sendWSToUserIdByText(ctx, to, remind);

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
