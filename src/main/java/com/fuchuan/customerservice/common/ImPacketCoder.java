package com.fuchuan.customerservice.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fuchuan.customerservice.common.payload.BasePayload;

import java.nio.ByteBuffer;

public class ImPacketCoder {
  public static String encodeToString(ImPacket packet) {
    return JSON.toJSONString(packet);
  }

  public static ImPacket decodeFromString(String packet) {
    JSONObject obj = JSON.parseObject(packet);
    ImPacket imPacket = jsonObjToImPacket(obj);
    return imPacket;
  }

  public static <T extends BasePayload> ImPacket<T> decodeFromString(
      String packet, Class<T> clazz) {
    ImPacket imPacket = JSON.parseObject(packet, ImPacket.class);
    T payload = BasePayload.from(imPacket.getPayload(), clazz);
    imPacket.setPayload(payload);
    return imPacket;
  }

  public static byte[] encode(ImPacket packet) {
    return JSON.toJSONBytes(packet);
  }

  public static ImPacket decode(byte[] packet) {
    JSONObject obj = JSON.parseObject(packet, JSONObject.class);
    ImPacket imPacket = jsonObjToImPacket(obj);
    return imPacket;
  }

  public static <T extends BasePayload> ImPacket<T> decode(byte[] packet, Class<T> clazz) {
    JSONObject obj = JSON.parseObject(packet, JSONObject.class);
    ImPacket imPacket = jsonObjToImPacket(obj);
    T payload = BasePayload.from(imPacket.getPayload(), clazz);
    imPacket.setPayload(payload);
    return imPacket;
  }

  private static ImPacket jsonObjToImPacket(JSONObject obj) {
    ImPacket imPacket = new ImPacket();
    imPacket.setCommand(Command.valueOf(obj.getString("command")));
    imPacket.setPayload(BasePayload.from(obj.getJSONObject("payload")));
    return imPacket;
  }

  public static void main(String[] args) {
    String t = "{\"command\":\"COMMAND_HEARTBEAT_REQ\",\"payload\":{}}";
    ImPacket imPacket = decodeFromString(t);
    imPacket = decode(t.getBytes());
    System.out.println(imPacket);
  }
}
