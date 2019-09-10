package com.fuchuan.customerservice.server;

import com.fuchuan.customerservice.common.AccountServiceGrpc;
import com.fuchuan.customerservice.db.IDao;
import com.fuchuan.customerservice.kit.ConfigKit;
import com.fuchuan.customerservice.kit.FstKit;
import com.fuchuan.customerservice.kit.ReflectKit;
import com.fuchuan.customerservice.server.config.ImServerConfig;
import com.fuchuan.customerservice.server.handler.IActualHandler;
import com.fuchuan.customerservice.server.handler.MainHandler;
import com.fuchuan.customerservice.server.handler.ActualHandlerMapper;
import com.fuchuan.customerservice.server.listener.ImIpStatListener;
import com.fuchuan.customerservice.server.listener.MainListener;
import io.lettuce.core.RedisURI;
import io.netty.channel.socket.DatagramChannel;
import io.netty.resolver.AddressResolverGroup;
import io.netty.resolver.dns.DnsServerAddressStreamProvider;
import org.codejargon.feather.Provides;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.connection.AddressResolverGroupFactory;
import org.tio.cluster.TioClusterConfig;
import org.tio.cluster.redisson.RedissonTioClusterTopic;
import org.tio.core.stat.IpStatListener;
import com.jfinal.kit.Prop;
import org.tio.websocket.server.WsServerAioListener;
import org.tio.websocket.server.handler.IWsMsgHandler;

import javax.inject.Singleton;
import java.net.InetSocketAddress;

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
      IpStatListener ipStatListener,
      TioClusterConfig tioClusterConfig)
      throws Exception {
    return new ImServer(config, handler, listener, ipStatListener, tioClusterConfig);
  }

//  @Provides
//  @Singleton
//  public ImServer[] imServersForTestCluster(
//      ImServerConfig config,
//      IWsMsgHandler handler,
//      WsServerAioListener listener,
//      IpStatListener ipStatListener,
//      TioClusterConfig tioClusterConfig)
//      throws Exception {
//    ImServerConfig config1 = FstKit.clone(config);
//    ImServerConfig config2 = FstKit.clone(config);
//    ImServerConfig config3 = FstKit.clone(config);
//    ReflectKit.setProp(config1, "bindPort", config.getBindPort() + 1);
//    ReflectKit.setProp(config2, "bindPort", config.getBindPort() + 2);
//    ReflectKit.setProp(config3, "bindPort", config.getBindPort() + 3);
//    return new ImServer[] {
//      new ImServer(
//          config1,
//          FstKit.clone(handler),
//          FstKit.clone(listener),
//          FstKit.clone(ipStatListener),
//          FstKit.clone(tioClusterConfig)),
//      new ImServer(
//          config2,
//          FstKit.clone(handler),
//          FstKit.clone(listener),
//          FstKit.clone(ipStatListener),
//          FstKit.clone(tioClusterConfig)),
//      new ImServer(
//          config3,
//          FstKit.clone(handler),
//          FstKit.clone(listener),
//          FstKit.clone(ipStatListener),
//          FstKit.clone(tioClusterConfig))
//    };
//  }

  @Provides
  @Singleton
  public IWsMsgHandler mainHandler(
      AccountServiceGrpc.AccountServiceBlockingStub accountService, IActualHandler realHandler,IDao dao) {
    return new MainHandler(accountService, realHandler,dao);
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

  @Provides
  @Singleton
  public TioClusterConfig tioClusterConfig(RedisURI uri) {
    Config config = new Config();
    SingleServerConfig singleServerConfig = config.useSingleServer();
    singleServerConfig.setAddress("redis://" + uri.getHost() + ":" + uri.getPort());
    if (uri.getPassword() != null) singleServerConfig.setPassword(new String(uri.getPassword()));
    RedissonClient client = Redisson.create(config);
    RedissonTioClusterTopic topic = new RedissonTioClusterTopic("customer-service", client);
    TioClusterConfig tioClusterConfig = new TioClusterConfig(topic);
    return tioClusterConfig;
  }
}
