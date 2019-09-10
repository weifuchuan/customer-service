package com.fuchuan.customerservice.db;

import com.fuchuan.customerservice.kit.FstKit;
import io.lettuce.core.codec.RedisCodec;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class FstRedisCodec implements RedisCodec<String, Object> {

  @Override
  public String decodeKey(ByteBuffer bytes) {
    return StandardCharsets.UTF_8.decode(bytes).toString();
  }

  @Override
  public Object decodeValue(ByteBuffer buf) {
    byte[] array = new byte[buf.remaining()];
    buf.get(array);
    return FstKit.deserialize(array);
  }

  @Override
  public ByteBuffer encodeKey(String key) {
    return StandardCharsets.UTF_8.encode(key);
  }

  @Override
  public ByteBuffer encodeValue(Object value) {
    return ByteBuffer.wrap(FstKit.serialize(value));
  }
}
