package com.fuchuan.customerservice.common;

import com.fuchuan.customerservice.common.payload.BasePayload;

import java.util.Map;

public class ImPacket<Payload extends BasePayload> {
  private Command command;
  private Payload payload;

  public Command getCommand() {
    return command;
  }

  public ImPacket<Payload> setCommand(Command command) {
    this.command = command;
    return this;
  }

  public Payload getPayload() {
    return payload;
  }

  public ImPacket<Payload> setPayload(Payload payload) {
    this.payload = payload;
    return this;
  }

  public static <T extends BasePayload> ImPacket<T> convert(ImPacket _packet, Class<T> clz) {
    T p = BasePayload.from(_packet.payload, clz);
    ImPacket<T> packet = new ImPacket<>();
    return packet.setCommand(_packet.command).setPayload(p);
  }

}
