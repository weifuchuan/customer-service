package com.fuchuan.customerservice;

import com.fuchuan.customerservice.db.DbModule;
import com.fuchuan.customerservice.kit.ConfigKit;
import com.fuchuan.customerservice.mock.grpc.GrpcServer;
import com.fuchuan.customerservice.server.ImServer;
import com.fuchuan.customerservice.server.ServerModule;
import com.fuchuan.customerservice.server.config.ImServerConfig;
import org.codejargon.feather.Feather;
import org.tio.http.common.HttpConfig;
import org.tio.http.server.HttpServerStarter;
import org.tio.http.server.handler.DefaultHttpRequestHandler;
import com.jfinal.kit.Prop;

import java.io.IOException;

public class App {

  public static final Feather feather =
      Feather.with(new CustomerServiceModule(), new DbModule(), new ServerModule());

  public static void main(String[] args) throws IOException {
    Prop config = feather.instance(Prop.class);
    startGrpcIfDevOrTest(args, config);
    startHttpServerIfTest(config);

    ImServer server = feather.instance(ImServer.class);
    server.start();
  }

  private static void startHttpServerIfTest(Prop config) {
    if (config.get("mode").startsWith("test")) {
      new Thread(
              () -> {
                HttpConfig httpConfig =
                    ConfigKit.createConfigObject(
                        config.getProperties(), ImServerConfig.class, "http");
                HttpServerStarter starter =
                    new HttpServerStarter(
                        httpConfig, new DefaultHttpRequestHandler(httpConfig, App.class));
                try {
                  starter.start();
                } catch (IOException e) {
                  e.printStackTrace();
                }
              })
          .start();
    }
  }

  private static void startGrpcIfDevOrTest(String[] args, Prop config) {
    if (config.get("mode").startsWith("dev") || config.get("mode").startsWith("test")) {
      new Thread(
              () -> {
                try {
                  GrpcServer.main(args);
                } catch (IOException | InterruptedException e) {
                  e.printStackTrace();
                }
              })
          .start();
    }
  }

}
