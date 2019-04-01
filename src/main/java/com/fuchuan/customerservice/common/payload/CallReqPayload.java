package com.fuchuan.customerservice.common.payload;

import java.util.Map;

public class CallReqPayload extends BasePayload {

  
  public CallReqPayload setAction(String action) {
    set("action", action);
    return this;
  }

  
  public String getAction() {
    return (String)get("action");
  }

  
  public CallReqPayload setId(String id) {
    set("id", id);
    return this;
  }

  
  public String getId() {
    return (String)get("id");
  }

  
  public CallReqPayload setPayload(String payload) {
    set("payload", payload);
    return this;
  }

  
  public String getPayload() {
    return (String)get("payload");
  }


  public static CallReqPayload from(Map obj) {
    CallReqPayload model = new CallReqPayload();
    model.set(obj);
    return model;
  }
}
