package com.fuchuan.customerservice.common.payload;

import java.util.Map;

public class OnlineOfflineNotifyPayload extends BasePayload {

  
  public OnlineOfflineNotifyPayload setLocalOnlineCountForUserId(Integer localOnlineCountForUserId) {
    set("localOnlineCountForUserId", localOnlineCountForUserId);
    return this;
  }

  
  public Integer getLocalOnlineCountForUserId() {
    return (Integer)get("localOnlineCountForUserId");
  }

  
  public OnlineOfflineNotifyPayload setUserId(String userId) {
    set("userId", userId);
    return this;
  }

  
  public String getUserId() {
    return (String)get("userId");
  }


  public static OnlineOfflineNotifyPayload from(Map obj) {
    OnlineOfflineNotifyPayload model = new OnlineOfflineNotifyPayload();
    model.set(obj);
    return model;
  }
}
