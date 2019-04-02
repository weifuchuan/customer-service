package com.fuchuan.customerservice.server.handler.actual;

import com.fuchuan.customerservice.common.Command;
import com.fuchuan.customerservice.common.ImPacket;
import com.fuchuan.customerservice.common.payload.ClearRemindReqPayload;
import com.fuchuan.customerservice.db.IDao;
import com.fuchuan.customerservice.server.handler.BaseActualHandler;
import com.fuchuan.customerservice.server.handler.HandlerForCommand;
import org.tio.core.ChannelContext;
import com.fuchuan.customerservice.server.websocket.common.WsRequest;

@HandlerForCommand(Command.COMMAND_CLEAR_REMIND_REQ)
public class ClearRemindHandler extends BaseActualHandler {
  public ClearRemindHandler(IDao dao) {
    super(dao);
  }

  @Override
  public Object handle(ImPacket _packet, WsRequest req, ChannelContext ctx) throws Exception {
    ImPacket<ClearRemindReqPayload> packet = ImPacket.convert(_packet, ClearRemindReqPayload.class);
    String roomKey = packet.getPayload().getRoomKey();
    dao.clearRemind(roomKey,ctx.userid);
    return null;
  }
}
