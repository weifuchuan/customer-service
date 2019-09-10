package com.fuchuan.customerservice.server;

import com.fuchuan.customerservice.server.config.ImServerConfig;
import org.tio.cluster.TioClusterConfig;
import org.tio.core.stat.IpStatListener;
import org.tio.server.ServerGroupContext;
import org.tio.websocket.server.WsServerAioListener;
import org.tio.websocket.server.WsServerStarter;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.io.IOException;

public class ImServer {
  private WsServerStarter starter;
  private ServerGroupContext serverGroupContext;

  public ImServer(
      ImServerConfig config,
      IWsMsgHandler handler,
      WsServerAioListener listener,
      IpStatListener ipStatListener,
      TioClusterConfig tioClusterConfig)
      throws Exception {
    starter = new WsServerStarter(config, handler);

    serverGroupContext = starter.getServerGroupContext();
    serverGroupContext.setName(config.getProtocolName());
    serverGroupContext.setServerAioListener(listener);

    serverGroupContext.setIpStatListener(ipStatListener);
    serverGroupContext.ipStats.addDurations(ImServerConfig.IpStatDuration.IPSTAT_DURATIONS);

    serverGroupContext.setTioClusterConfig(tioClusterConfig);

    // SSL
    if (config.isUseSsl()) {
      String keyStoreFile = config.getSslKeystore();
      String trustStoreFile = config.getSslTruststore();
      String keyStorePwd = config.getSslpPwd();
      serverGroupContext.useSsl(keyStoreFile, trustStoreFile, keyStorePwd);
    }
  }

  public void start() throws IOException {
    starter.start();
  }

  public ServerGroupContext getServerGroupContext() {
    return serverGroupContext;
  }
}
