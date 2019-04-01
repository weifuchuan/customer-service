package com.fuchuan.customerservice.server.handler;

import org.tio.core.ChannelContext;
import org.tio.websocket.common.WsRequest;

@Deprecated
public interface ICloseHook {
  void onClose(WsRequest req, byte[] bytes, ChannelContext ctx) throws Exception;
}
