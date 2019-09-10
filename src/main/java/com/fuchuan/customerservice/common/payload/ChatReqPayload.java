package com.fuchuan.customerservice.common.payload;

import java.util.Map;

public class ChatReqPayload extends BasePayload {

  // 内容
  public ChatReqPayload setContent(String content) {
    set("content", content);
    return this;
  }

  // 内容
  public String getContent() {
    return (String)get("content");
  }

  // 发送到 用户id || 群号
  public ChatReqPayload setTo(String to) {
    set("to", to);
    return this;
  }

  // 发送到 用户id || 群号
  public String getTo() {
    return (String)get("to");
  }

  // 聊天室类型：1对1（1）、群聊（2）
  public ChatReqPayload setType(Integer type) {
    set("type", type);
    return this;
  }

  // 聊天室类型：1对1（1）、群聊（2）
  public Integer getType() {
    return (Integer)get("type");
  }


  public static ChatReqPayload from(Map obj) {
    ChatReqPayload model = new ChatReqPayload();
    model.set(obj);
    return model;
  }
}
