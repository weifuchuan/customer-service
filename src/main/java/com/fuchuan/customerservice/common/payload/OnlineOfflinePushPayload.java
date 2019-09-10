package com.fuchuan.customerservice.common.payload;

import java.util.Map;

public class OnlineOfflinePushPayload extends BasePayload {

  
  public OnlineOfflinePushPayload setAvatar(String avatar) {
    set("avatar", avatar);
    return this;
  }

  
  public String getAvatar() {
    return (String)get("avatar");
  }

  
  public OnlineOfflinePushPayload setId(String id) {
    set("id", id);
    return this;
  }

  
  public String getId() {
    return (String)get("id");
  }

  
  public OnlineOfflinePushPayload setNickName(String nickName) {
    set("nickName", nickName);
    return this;
  }

  
  public String getNickName() {
    return (String)get("nickName");
  }


  public static OnlineOfflinePushPayload from(Map obj) {
    OnlineOfflinePushPayload model = new OnlineOfflinePushPayload();
    model.set(obj);
    return model;
  }
}
