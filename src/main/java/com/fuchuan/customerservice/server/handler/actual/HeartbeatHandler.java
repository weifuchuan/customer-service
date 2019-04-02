package com.fuchuan.customerservice.server.handler.actual;

import com.fuchuan.customerservice.common.Command;
import com.fuchuan.customerservice.common.ImPacket;
import com.fuchuan.customerservice.common.payload.HeartbeatPayload;
import com.fuchuan.customerservice.db.IDao;
import com.fuchuan.customerservice.kit.TioKit;
import com.fuchuan.customerservice.server.handler.BaseActualHandler;
import com.fuchuan.customerservice.server.handler.HandlerForCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import com.fuchuan.customerservice.server.websocket.common.WsRequest;

@HandlerForCommand(Command.COMMAND_HEARTBEAT_REQ)
public class HeartbeatHandler extends BaseActualHandler {
  private static final Logger log = LoggerFactory.getLogger(HeartbeatHandler.class);

  public static final ImPacket<HeartbeatPayload> respPacket =
      new ImPacket<HeartbeatPayload>()
          .setCommand(Command.COMMAND_HEARTBEAT_RESP)
          .setPayload(new HeartbeatPayload());

  public HeartbeatHandler(IDao dao) {
    super(dao);
  }

  @Override
  public Object handle(ImPacket _packet, WsRequest req, ChannelContext ctx) {
    TioKit.sendWsByText(ctx, respPacket);
    return null;
  }
}
