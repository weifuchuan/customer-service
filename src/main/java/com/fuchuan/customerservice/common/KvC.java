package com.fuchuan.customerservice.common;

import com.jfinal.json.Json;
import com.jfinal.kit.StrKit;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@SuppressWarnings({"serial", "rawtypes", "unchecked"})
public class KvC extends ConcurrentHashMap {

  @Deprecated private static final String STATE_OK = "isOk";
  @Deprecated private static final String STATE_FAIL = "isFail";

  public KvC() {}

  public static KvC by(Object key, Object value) {
    return new KvC().set(key, value);
  }

  public static KvC create() {
    return new KvC();
  }

  public KvC set(Object key, Object value) {
    super.put(key, value);
    return this;
  }

  public KvC setIfNotBlank(Object key, String value) {
    if (StrKit.notBlank(value)) {
      set(key, value);
    }
    return this;
  }

  public KvC setIfNotNull(Object key, Object value) {
    if (value != null) {
      set(key, value);
    }
    return this;
  }

  public KvC set(Map map) {
    super.putAll(map);
    return this;
  }

  public KvC set(KvC kv) {
    super.putAll(kv);
    return this;
  }

  public KvC delete(Object key) {
    super.remove(key);
    return this;
  }

  public <T> T getAs(Object key) {
    return (T) get(key);
  }

  public String getStr(Object key) {
    Object s = get(key);
    return s != null ? s.toString() : null;
  }

  public Integer getInt(Object key) {
    Number n = (Number) get(key);
    return n != null ? n.intValue() : null;
  }

  public Long getLong(Object key) {
    Number n = (Number) get(key);
    return n != null ? n.longValue() : null;
  }

  public Number getNumber(Object key) {
    return (Number) get(key);
  }

  public Boolean getBoolean(Object key) {
    return (Boolean) get(key);
  }

  /** key 存在，并且 value 不为 null */
  public boolean notNull(Object key) {
    return get(key) != null;
  }

  /** key 不存在，或者 key 存在但 value 为null */
  public boolean isNull(Object key) {
    return get(key) == null;
  }

  /** key 存在，并且 value 为 true，则返回 true */
  public boolean isTrue(Object key) {
    Object value = get(key);
    return (value instanceof Boolean && ((Boolean) value == true));
  }

  /** key 存在，并且 value 为 false，则返回 true */
  public boolean isFalse(Object key) {
    Object value = get(key);
    return (value instanceof Boolean && ((Boolean) value == false));
  }

  public String toJson() {
    return Json.getJson().toJson(this);
  }

  public boolean equals(Object kv) {
    return kv instanceof KvC && super.equals(kv);
  }
}
