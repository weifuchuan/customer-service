package com.fuchuan.customerservice.common.payload;

import java.util.Map;

public class _PayloadModel extends BasePayload {
  public _PayloadModel setProp(Object prop) {
    set("prop", prop);
    return this;
  }

  public Object getProp() {
    return get("prop");
  }

  public static _PayloadModel from(Map obj) {
    _PayloadModel model = new _PayloadModel();
    model.set(obj);
    return model;
  }
}
