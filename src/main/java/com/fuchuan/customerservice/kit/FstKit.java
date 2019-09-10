package com.fuchuan.customerservice.kit;

import org.nustaq.serialization.FSTConfiguration;

import java.io.Serializable;

public class FstKit {
  private static FSTConfiguration fst = FSTConfiguration.createDefaultConfiguration();

  public static byte[] serialize(Object obj) {
    if (obj == null) return null;
    return fst.asByteArray(obj);
  }

  public static Object deserialize(byte[] bytes) {
    if (bytes == null || bytes.length == 0) {
      return null;
    }
    try {
      return fst.asObject(bytes);
    } catch (Throwable ex) {
    }
    return null;
  }

  public static <T extends Serializable> T clone(T obj){
    return (T) deserialize(serialize(obj));
  }
}
