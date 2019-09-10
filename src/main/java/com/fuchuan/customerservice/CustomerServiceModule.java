package com.fuchuan.customerservice;

import com.fuchuan.customerservice.common.AccountServiceGrpc;
import com.google.protobuf.BlockingRpcChannel;
import com.jfinal.kit.Prop;
import io.grpc.ManagedChannelBuilder;
import org.codejargon.feather.Provides;

import javax.inject.Singleton;

public class CustomerServiceModule {
  @Provides
  @Singleton
  public Prop config() {
    return new Prop("config.properties");
  }

  @Provides
  @Singleton
  public AccountServiceGrpc.AccountServiceBlockingStub accountServiceBlockingStub(Prop config) {
    String host = config.get("grpc.host").trim();
    int port = Integer.valueOf(config.get("grpc.port").trim());
    return AccountServiceGrpc.newBlockingStub(
        ManagedChannelBuilder.forAddress(host, port)
            // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
            // needing certificates.
            .usePlaintext()
            .build());
  }
}
