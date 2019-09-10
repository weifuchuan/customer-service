package com.fuchuan.customerservice.common.payload;

import java.util.Map;

public class HeartbeatPayload extends BasePayload {


  public static HeartbeatPayload from(Map obj) {
    HeartbeatPayload model = new HeartbeatPayload();
    model.set(obj);
    return model;
  }
}
