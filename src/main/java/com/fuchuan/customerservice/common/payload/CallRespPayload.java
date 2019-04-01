package com.fuchuan.customerservice.common.payload;

import java.util.Map;

public class CallRespPayload extends BasePayload {

  
  public CallRespPayload setId(String id) {
    set("id", id);
    return this;
  }

  
  public String getId() {
    return (String)get("id");
  }

  
  public CallRespPayload setRet(String ret) {
    set("ret", ret);
    return this;
  }

  
  public String getRet() {
    return (String)get("ret");
  }


  public static CallRespPayload from(Map obj) {
    CallRespPayload model = new CallRespPayload();
    model.set(obj);
    return model;
  }
}
