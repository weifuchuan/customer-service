package com.fuchuan.customerservice.common.payload;

import com.jfinal.kit.Kv;

import java.util.Map;

public class BasePayload extends Kv {
  public static BasePayload from(Map obj) {
    return from(obj, BasePayload.class);
  }

  public static <T extends BasePayload> T from(Map obj, Class<T> clazz) {
    try {
      T instance = clazz.newInstance();
      return (T) instance.set(obj);
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return null;
  }
}
