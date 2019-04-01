package com.fuchuan.customerservice.common.model;

import java.util.HashMap;

public class _Model extends HashMap {

  public _Model setProp(Object prop) {
    return set("prop", prop);
  }

  public Object getProp() {
    return get("prop");
  }

  public _Model set(String key, Object val) {
    put(key, val);
    return this;
  }
}
