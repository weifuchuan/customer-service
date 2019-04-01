package com.fuchuan.customerservice.server.handler;

import org.tio.core.ChannelContext;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;

@Deprecated
public interface IHandshakeHook {
  void handshake(HttpRequest req, HttpResponse resp, ChannelContext ctx)throws Exception;
}
