package com.fuchuan.customerservice.server;

import com.fuchuan.customerservice.common.AccountServiceGrpc;
import com.fuchuan.customerservice.db.IDao;
import com.fuchuan.customerservice.kit.ConfigKit;
import com.fuchuan.customerservice.server.config.ImServerConfig;
import com.fuchuan.customerservice.server.handler.IActualHandler;
import com.fuchuan.customerservice.server.handler.MainHandler;
import com.fuchuan.customerservice.server.handler.ActualHandlerMapper;
import com.fuchuan.customerservice.server.listener.ImIpStatListener;
import com.fuchuan.customerservice.server.listener.MainListener;
import org.codejargon.feather.Provides;
import org.tio.core.stat.IpStatListener;
import org.tio.utils.jfinal.Prop;
import org.tio.websocket.server.WsServerAioListener;
import org.tio.websocket.server.handler.IWsMsgHandler;

import javax.inject.Singleton;

public class ServerModule {
  @Provides
  @Singleton
  public ImServerConfig config(Prop config) {
    return ConfigKit.createConfigObject(config.getProperties(), ImServerConfig.class, "ws");
  }

  @Provides
  @Singleton
  public ImServer imServer(
      ImServerConfig config,
      IWsMsgHandler handler,
      WsServerAioListener listener,
      IpStatListener ipStatListener)
      throws Exception {
    return new ImServer(config, handler, listener, ipStatListener);
  }

  @Provides
  @Singleton
  public IWsMsgHandler mainHandler(
      AccountServiceGrpc.AccountServiceBlockingStub accountService, IActualHandler realHandler) {
    return new MainHandler(accountService, realHandler);
  }

  @Provides
  @Singleton
  public WsServerAioListener mainListener() {
    return new MainListener();
  }

  @Provides
  @Singleton
  public IpStatListener ipStatListener() {
    return new ImIpStatListener();
  }

  @Provides
  @Singleton
  public IActualHandler realHandler(IDao dao) {
    return new ActualHandlerMapper(dao);
  }
}
