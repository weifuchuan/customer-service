package com.fuchuan.customerservice.common.model;

import java.util.HashMap;
import java.util.Map;

public class MessageDetailModel extends HashMap {
  
  // 消息内容
  public MessageDetailModel setContent(String content) {
    return set("content", content);
  }

  // 消息内容
  public String getContent() {
    return (String)get("content");
  }
  
  
  public MessageDetailModel setFrom(String from) {
    return set("from", from);
  }

  
  public String getFrom() {
    return (String)get("from");
  }
  
  
  public MessageDetailModel setMsgKey(String msgKey) {
    return set("msgKey", msgKey);
  }

  
  public String getMsgKey() {
    return (String)get("msgKey");
  }
  
  
  public MessageDetailModel setSendAt(Long sendAt) {
    return set("sendAt", sendAt);
  }

  
  public Long getSendAt() {
    return (Long)get("sendAt");
  }
  
  
  public MessageDetailModel setTo(String to) {
    return set("to", to);
  }

  
  public String getTo() {
    return (String)get("to");
  }
  

  public MessageDetailModel set(String key, Object val) {
    put(key, val);
    return this;
  }

  public static MessageDetailModel from(Map obj) {
      MessageDetailModel model = new MessageDetailModel();
      model.putAll(obj);
      return model;
  }
}
