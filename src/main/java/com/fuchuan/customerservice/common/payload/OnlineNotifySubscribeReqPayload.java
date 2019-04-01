package com.fuchuan.customerservice.common.payload;

import java.util.Map;

public class OnlineNotifySubscribeReqPayload extends BasePayload {

  
  public OnlineNotifySubscribeReqPayload setWho(com.alibaba.fastjson.JSONArray who) {
    set("who", who);
    return this;
  }

  
  public com.alibaba.fastjson.JSONArray getWho() {
    return (com.alibaba.fastjson.JSONArray)get("who");
  }


  public static OnlineNotifySubscribeReqPayload from(Map obj) {
    OnlineNotifySubscribeReqPayload model = new OnlineNotifySubscribeReqPayload();
    model.set(obj);
    return model;
  }
}
