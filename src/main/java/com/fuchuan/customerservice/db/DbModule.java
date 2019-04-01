package com.fuchuan.customerservice.db;

import com.fuchuan.customerservice.kit.ConfigKit;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.RedisCodec;
import org.codejargon.feather.Provides;
import org.tio.utils.jfinal.Prop;

import javax.inject.Singleton;

public class DbModule {

  @Provides
  @Singleton
  public RedisClient redisClient(Prop config) {
    RedisURI uri = ConfigKit.createConfigObject(config.getProperties(), RedisURI.class, "redis");
    RedisClient client = RedisClient.create(uri);
    R.init(client);
    return client;
  }

  @Provides
  @Singleton
  public StatefulRedisConnection<String, Object> redisConnection(
      RedisClient redisClient, RedisCodec<String, Object> redisCodec) {
    return redisClient.connect(redisCodec);
  }

  @Provides
  @Singleton
  public RedisCodec<String, Object> redisCodec() {
    return new FstRedisCodec();
  }

  @Provides
  @Singleton
  public IDao dao(RedisClient redisClient /* for exec R.init() */) {
    return new DaoByRedis();
  }
}
