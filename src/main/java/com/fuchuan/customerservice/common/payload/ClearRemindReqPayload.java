package com.fuchuan.customerservice.common.payload;

import java.util.Map;

public class ClearRemindReqPayload extends BasePayload {

  
  public ClearRemindReqPayload setRoomKey(String roomKey) {
    set("roomKey", roomKey);
    return this;
  }

  
  public String getRoomKey() {
    return (String)get("roomKey");
  }


  public static ClearRemindReqPayload from(Map obj) {
    ClearRemindReqPayload model = new ClearRemindReqPayload();
    model.set(obj);
    return model;
  }
}
