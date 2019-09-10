package com.fuchuan.customerservice.common.payload;

import java.util.Map;

public class RemindPushPayload extends BasePayload {

  // 消息内容
  public RemindPushPayload setContent(String content) {
    set("content", content);
    return this;
  }

  // 消息内容
  public String getContent() {
    return (String)get("content");
  }

  
  public RemindPushPayload setFrom(String from) {
    set("from", from);
    return this;
  }

  
  public String getFrom() {
    return (String)get("from");
  }

  
  public RemindPushPayload setMsgKey(String msgKey) {
    set("msgKey", msgKey);
    return this;
  }

  
  public String getMsgKey() {
    return (String)get("msgKey");
  }

  
  public RemindPushPayload setRoomKey(String roomKey) {
    set("roomKey", roomKey);
    return this;
  }

  
  public String getRoomKey() {
    return (String)get("roomKey");
  }

  
  public RemindPushPayload setSendAt(Long sendAt) {
    set("sendAt", sendAt);
    return this;
  }

  
  public Long getSendAt() {
    return (Long)get("sendAt");
  }

  
  public RemindPushPayload setTo(String to) {
    set("to", to);
    return this;
  }

  
  public String getTo() {
    return (String)get("to");
  }


  public static RemindPushPayload from(Map obj) {
    RemindPushPayload model = new RemindPushPayload();
    model.set(obj);
    return model;
  }
}
