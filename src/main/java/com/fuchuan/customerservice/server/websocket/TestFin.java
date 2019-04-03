package com.fuchuan.customerservice.server.websocket;

import cn.hutool.core.io.FileUtil;
import com.fuchuan.customerservice.server.websocket.common.WsRequest;
import com.fuchuan.customerservice.server.websocket.server.WsServerConfig;
import com.fuchuan.customerservice.server.websocket.server.WsServerStarter;
import com.fuchuan.customerservice.server.websocket.server.handler.IWsMsgHandler;
import org.tio.core.ChannelContext;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;

import java.io.IOException;

public class TestFin {
  public static void main(String[] args) throws IOException {
    WsServerConfig config = new WsServerConfig(9999);
    WsServerStarter starter =
      new WsServerStarter(
        config,
        new IWsMsgHandler() {
          @Override
          public HttpResponse handshake(
            HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext)
            throws Exception {
            return httpResponse;
          }

          @Override
          public void onAfterHandshaked(
            HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext)
            throws Exception {
          }

          @Override
          public Object onBytes(
            WsRequest wsRequest, byte[] bytes, ChannelContext channelContext)
            throws Exception {
            FileUtil.writeBytes(bytes, "C:\\Users\\fuchu\\Documents\\test.exe");
            return null;
          }

          @Override
          public Object onClose(
            WsRequest wsRequest, byte[] bytes, ChannelContext channelContext)
            throws Exception {
            return null;
          }

          @Override
          public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext)
            throws Exception {
            return null;
          }
        });
    starter.start();
  }
}

/*
function selectFiles(
  inputPropsSetter
) {
  return new Promise((resolve, reject) => {
    const input = document.createElement('input');
    input.multiple = false;
    inputPropsSetter && inputPropsSetter(input);
    input.type = 'file';
    input.style.display = 'none';
    input.onchange = () => {
      if (input.files) {
        const files  = [];
        for (let i = 0; i < input.files.length; i++) {
          files.push(input.files.item(i));
        }
        resolve(files);
      } else {
        reject();
      }
    };
    document.getElementsByTagName('html').item(0).appendChild(input);
    input.click();
  });
}


 */