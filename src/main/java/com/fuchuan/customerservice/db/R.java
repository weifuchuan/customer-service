package com.fuchuan.customerservice.db;

import com.fuchuan.customerservice.kit.ProxyKit;
import com.jfinal.kit.Kv;
import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.output.*;
import io.lettuce.core.protocol.CommandArgs;
import io.lettuce.core.protocol.CommandType;
import io.lettuce.core.protocol.ProtocolKeyword;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class R {
  private static RedisClient client;
  private static StatefulRedisConnection<String, Object> conn;
  private static StatefulRedisConnection<String, Object> syncConn;
  private static StatefulRedisConnection<String, Object> asyncConn;
  private static StatefulRedisConnection<String, Object> pipeConn;

  private static final RedisCodec<String, Object> codec = new FstRedisCodec();

  public static void init(RedisURI uri) {
    client = RedisClient.create(uri);
    R.init(client);
  }

  public static void init(RedisClient client) {
    R.client = client;

    conn = client.connect(codec);
    syncConn = client.connect(codec);
    asyncConn = client.connect(codec);
    pipeConn = client.connect(codec);
  }

  public static void close() {
    client.shutdown();
  }

  public static RedisClient getClient() {
    return client;
  }

  public static RedisClient c() {
    return client;
  }

  public static StatefulRedisConnection<String, Object> connect() {
    return conn;
  }

  public static RedisAsyncCommands<String, Object> async() {
    return asyncConn.async();
  }

  public static RedisCommands<String, Object> sync() {
    return syncConn.sync();
  }

  public static void pipeNotRetAsync(Consumer<RedisAsyncCommands<String, Object>> consumer){
    RedisAsyncCommands<String, Object> async = async();
    async.setAutoFlushCommands(false);
    consumer.accept(async);
    async.flushCommands();
  }

  public static List<Object> pipe(Consumer<RedisAsyncCommands<String, Object>> consumer)
      throws ExecutionException, InterruptedException {
    return pipe(consumer, 30);
  }

  public static List<Object> pipe(
      Consumer<RedisAsyncCommands<String, Object>> consumer, int timeoutSecond)
      throws ExecutionException, InterruptedException {
    RedisAsyncCommands<String, Object> async = pipeConn.async();

    List<RedisFuture> futureList = new ArrayList<>();

    RedisAsyncCommands<String, Object> proxyAsync =
        ProxyKit.intercept(
            async,
            EmptyRedisAsyncCommandsImpl.class,
            (ret, method, args) -> {
              if (ret instanceof RedisFuture) {
                futureList.add((RedisFuture) ret);
              }
            });

    async.setAutoFlushCommands(false);

    consumer.accept(proxyAsync);

    async.flushCommands();

    LettuceFutures.awaitAll(
        timeoutSecond, TimeUnit.SECONDS, futureList.toArray(new RedisFuture[0]));

    return futureList.stream()
        .map(
            future -> {
              try {
                return future.get();
              } catch (InterruptedException|ExecutionException e) {
                e.printStackTrace();
              }
              return null;
            })
        .collect(Collectors.toList());
  }

  public static RedisFuture<TransactionResult> multi(
      Consumer<RedisAsyncCommands<String, Object>> consumer)
      throws ExecutionException, InterruptedException {
    RedisAsyncCommands<String, Object> async = client.connect(codec).async();
    async.multi();
    consumer.accept(async);
    return async.exec();
  }

  public static RedisReactiveCommands<String, Object> reactive() {
    return connect().reactive();
  }

  public static List<RedisFuture<Boolean>> setHash(
      RedisAsyncCommands<String, Object> async, String key, Map data) {
    List<RedisFuture<Boolean>> futures = new ArrayList<>();
    data.forEach(
        (k, v) -> {
          RedisFuture<Boolean> future = async.hset(key, String.valueOf(k), v);
          futures.add(future);
        });
    return futures;
  }

  public static List<RedisFuture<Boolean>> setHash(String key, Map data) {
    RedisAsyncCommands<String, Object> async = async();
    async.setAutoFlushCommands(false);
    List<RedisFuture<Boolean>> futures = new ArrayList<>();
    data.forEach(
      (k, v) -> {
        RedisFuture<Boolean> future = async.hset(key, String.valueOf(k), v);
        futures.add(future);
      });
    async.flushCommands();
    return futures;
  }

  public static String nextId() {
    RedisCommands<String, Object> sync = R.sync();
    Long nextId = sync.incr("im:nextIdForAll");
    if (nextId == null) return "0";
    return nextId.toString();
  }


  public static class EmptyRedisAsyncCommandsImpl implements RedisAsyncCommands<String, Object> {
    public EmptyRedisAsyncCommandsImpl() {}

    @Override
    public void setTimeout(Duration timeout) {}

    @Override
    public void setTimeout(long timeout, TimeUnit unit) {}

    @Override
    public String auth(String password) {
      return null;
    }

    @Override
    public RedisFuture<String> clusterBumpepoch() {
      return null;
    }

    @Override
    public RedisFuture<String> clusterMeet(String ip, int port) {
      return null;
    }

    @Override
    public RedisFuture<String> clusterForget(String nodeId) {
      return null;
    }

    @Override
    public RedisFuture<String> clusterAddSlots(int... slots) {
      return null;
    }

    @Override
    public RedisFuture<String> clusterDelSlots(int... slots) {
      return null;
    }

    @Override
    public RedisFuture<String> clusterSetSlotNode(int slot, String nodeId) {
      return null;
    }

    @Override
    public RedisFuture<String> clusterSetSlotStable(int slot) {
      return null;
    }

    @Override
    public RedisFuture<String> clusterSetSlotMigrating(int slot, String nodeId) {
      return null;
    }

    @Override
    public RedisFuture<String> clusterSetSlotImporting(int slot, String nodeId) {
      return null;
    }

    @Override
    public RedisFuture<String> clusterInfo() {
      return null;
    }

    @Override
    public RedisFuture<String> clusterMyId() {
      return null;
    }

    @Override
    public RedisFuture<String> clusterNodes() {
      return null;
    }

    @Override
    public RedisFuture<List<String>> clusterSlaves(String nodeId) {
      return null;
    }

    @Override
    public RedisFuture<List<String>> clusterGetKeysInSlot(int slot, int count) {
      return null;
    }

    @Override
    public RedisFuture<Long> clusterCountKeysInSlot(int slot) {
      return null;
    }

    @Override
    public RedisFuture<Long> clusterCountFailureReports(String nodeId) {
      return null;
    }

    @Override
    public RedisFuture<Long> clusterKeyslot(String key) {
      return null;
    }

    @Override
    public RedisFuture<String> clusterSaveconfig() {
      return null;
    }

    @Override
    public RedisFuture<String> clusterSetConfigEpoch(long configEpoch) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> clusterSlots() {
      return null;
    }

    @Override
    public RedisFuture<String> asking() {
      return null;
    }

    @Override
    public RedisFuture<String> clusterReplicate(String nodeId) {
      return null;
    }

    @Override
    public RedisFuture<String> clusterFailover(boolean force) {
      return null;
    }

    @Override
    public RedisFuture<String> clusterReset(boolean hard) {
      return null;
    }

    @Override
    public RedisFuture<String> clusterFlushslots() {
      return null;
    }

    @Override
    public RedisFuture<Long> del(String... keys) {
      return null;
    }

    @Override
    public RedisFuture<Long> unlink(String... keys) {
      return null;
    }

    @Override
    public RedisFuture<byte[]> dump(String key) {
      return null;
    }

    @Override
    public RedisFuture<Long> exists(String... keys) {
      return null;
    }

    @Override
    public RedisFuture<Boolean> expire(String key, long seconds) {
      return null;
    }

    @Override
    public RedisFuture<Boolean> expireat(String key, Date timestamp) {
      return null;
    }

    @Override
    public RedisFuture<Boolean> expireat(String key, long timestamp) {
      return null;
    }

    @Override
    public RedisFuture<List<String>> keys(String pattern) {
      return null;
    }

    @Override
    public RedisFuture<Long> keys(KeyStreamingChannel<String> channel, String pattern) {
      return null;
    }

    @Override
    public RedisFuture<String> migrate(String host, int port, String key, int db, long timeout) {
      return null;
    }

    @Override
    public RedisFuture<String> migrate(
      String host, int port, int db, long timeout, MigrateArgs<String> migrateArgs) {
      return null;
    }

    @Override
    public RedisFuture<Boolean> move(String key, int db) {
      return null;
    }

    @Override
    public RedisFuture<String> objectEncoding(String key) {
      return null;
    }

    @Override
    public RedisFuture<Long> objectIdletime(String key) {
      return null;
    }

    @Override
    public RedisFuture<Long> objectRefcount(String key) {
      return null;
    }

    @Override
    public RedisFuture<Boolean> persist(String key) {
      return null;
    }

    @Override
    public RedisFuture<Boolean> pexpire(String key, long milliseconds) {
      return null;
    }

    @Override
    public RedisFuture<Boolean> pexpireat(String key, Date timestamp) {
      return null;
    }

    @Override
    public RedisFuture<Boolean> pexpireat(String key, long timestamp) {
      return null;
    }

    @Override
    public RedisFuture<Long> pttl(String key) {
      return null;
    }

    @Override
    public RedisFuture<Object> randomkey() {
      return null;
    }

    @Override
    public RedisFuture<String> rename(String key, String newKey) {
      return null;
    }

    @Override
    public RedisFuture<Boolean> renamenx(String key, String newKey) {
      return null;
    }

    @Override
    public RedisFuture<String> restore(String key, long ttl, byte[] value) {
      return null;
    }

    @Override
    public RedisFuture<String> restore(String key, byte[] value, RestoreArgs args) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> sort(String key) {
      return null;
    }

    @Override
    public RedisFuture<Long> sort(ValueStreamingChannel<Object> channel, String key) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> sort(String key, SortArgs sortArgs) {
      return null;
    }

    @Override
    public RedisFuture<Long> sort(
      ValueStreamingChannel<Object> channel, String key, SortArgs sortArgs) {
      return null;
    }

    @Override
    public RedisFuture<Long> sortStore(String key, SortArgs sortArgs, String destination) {
      return null;
    }

    @Override
    public RedisFuture<Long> touch(String... keys) {
      return null;
    }

    @Override
    public RedisFuture<Long> ttl(String key) {
      return null;
    }

    @Override
    public RedisFuture<String> type(String key) {
      return null;
    }

    @Override
    public RedisFuture<KeyScanCursor<String>> scan() {
      return null;
    }

    @Override
    public RedisFuture<KeyScanCursor<String>> scan(ScanArgs scanArgs) {
      return null;
    }

    @Override
    public RedisFuture<KeyScanCursor<String>> scan(ScanCursor scanCursor, ScanArgs scanArgs) {
      return null;
    }

    @Override
    public RedisFuture<KeyScanCursor<String>> scan(ScanCursor scanCursor) {
      return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> scan(KeyStreamingChannel<String> channel) {
      return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> scan(
      KeyStreamingChannel<String> channel, ScanArgs scanArgs) {
      return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> scan(
      KeyStreamingChannel<String> channel, ScanCursor scanCursor, ScanArgs scanArgs) {
      return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> scan(
      KeyStreamingChannel<String> channel, ScanCursor scanCursor) {
      return null;
    }

    @Override
    public RedisFuture<Long> append(String key, Object value) {
      return null;
    }

    @Override
    public RedisFuture<Long> bitcount(String key) {
      return null;
    }

    @Override
    public RedisFuture<Long> bitcount(String key, long start, long end) {
      return null;
    }

    @Override
    public RedisFuture<List<Long>> bitfield(String key, BitFieldArgs bitFieldArgs) {
      return null;
    }

    @Override
    public RedisFuture<Long> bitpos(String key, boolean state) {
      return null;
    }

    @Override
    public RedisFuture<Long> bitpos(String key, boolean state, long start) {
      return null;
    }

    @Override
    public RedisFuture<Long> bitpos(String key, boolean state, long start, long end) {
      return null;
    }

    @Override
    public RedisFuture<Long> bitopAnd(String destination, String... keys) {
      return null;
    }

    @Override
    public RedisFuture<Long> bitopNot(String destination, String source) {
      return null;
    }

    @Override
    public RedisFuture<Long> bitopOr(String destination, String... keys) {
      return null;
    }

    @Override
    public RedisFuture<Long> bitopXor(String destination, String... keys) {
      return null;
    }

    @Override
    public RedisFuture<Long> decr(String key) {
      return null;
    }

    @Override
    public RedisFuture<Long> decrby(String key, long amount) {
      return null;
    }

    @Override
    public RedisFuture<Object> get(String key) {
      return null;
    }

    @Override
    public RedisFuture<Long> getbit(String key, long offset) {
      return null;
    }

    @Override
    public RedisFuture<Object> getrange(String key, long start, long end) {
      return null;
    }

    @Override
    public RedisFuture<Object> getset(String key, Object value) {
      return null;
    }

    @Override
    public RedisFuture<Long> incr(String key) {
      return null;
    }

    @Override
    public RedisFuture<Long> incrby(String key, long amount) {
      return null;
    }

    @Override
    public RedisFuture<Double> incrbyfloat(String key, double amount) {
      return null;
    }

    @Override
    public RedisFuture<List<KeyValue<String, Object>>> mget(String... keys) {
      return null;
    }

    @Override
    public RedisFuture<Long> mget(
      KeyValueStreamingChannel<String, Object> channel, String... keys) {
      return null;
    }

    @Override
    public RedisFuture<String> mset(Map<String, Object> map) {
      return null;
    }

    @Override
    public RedisFuture<Boolean> msetnx(Map<String, Object> map) {
      return null;
    }

    @Override
    public RedisFuture<String> set(String key, Object value) {
      return null;
    }

    @Override
    public RedisFuture<String> set(String key, Object value, SetArgs setArgs) {
      return null;
    }

    @Override
    public RedisFuture<Long> setbit(String key, long offset, int value) {
      return null;
    }

    @Override
    public RedisFuture<String> setex(String key, long seconds, Object value) {
      return null;
    }

    @Override
    public RedisFuture<String> psetex(String key, long milliseconds, Object value) {
      return null;
    }

    @Override
    public RedisFuture<Boolean> setnx(String key, Object value) {
      return null;
    }

    @Override
    public RedisFuture<Long> setrange(String key, long offset, Object value) {
      return null;
    }

    @Override
    public RedisFuture<Long> strlen(String key) {
      return null;
    }

    @Override
    public String select(int db) {
      return null;
    }

    @Override
    public RedisFuture<String> swapdb(int db1, int db2) {
      return null;
    }

    @Override
    public StatefulRedisConnection<String, Object> getStatefulConnection() {
      return null;
    }

    @Override
    public RedisFuture<Long> publish(String channel, Object message) {
      return null;
    }

    @Override
    public RedisFuture<List<String>> pubsubChannels() {
      return null;
    }

    @Override
    public RedisFuture<List<String>> pubsubChannels(String channel) {
      return null;
    }

    @Override
    public RedisFuture<Map<String, Long>> pubsubNumsub(String... channels) {
      return null;
    }

    @Override
    public RedisFuture<Long> pubsubNumpat() {
      return null;
    }

    @Override
    public RedisFuture<Object> echo(Object msg) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> role() {
      return null;
    }

    @Override
    public RedisFuture<String> ping() {
      return null;
    }

    @Override
    public RedisFuture<String> readOnly() {
      return null;
    }

    @Override
    public RedisFuture<String> readWrite() {
      return null;
    }

    @Override
    public RedisFuture<String> quit() {
      return null;
    }

    @Override
    public RedisFuture<Long> waitForReplication(int replicas, long timeout) {
      return null;
    }

    @Override
    public <T> RedisFuture<T> dispatch(
      ProtocolKeyword type, CommandOutput<String, Object, T> output) {
      return null;
    }

    @Override
    public <T> RedisFuture<T> dispatch(
      ProtocolKeyword type,
      CommandOutput<String, Object, T> output,
      CommandArgs<String, Object> args) {
      return null;
    }

    @Override
    public boolean isOpen() {
      return false;
    }

    @Override
    public void reset() {}

    @Override
    public void setAutoFlushCommands(boolean autoFlush) {}

    @Override
    public void flushCommands() {}

    @Override
    public RedisFuture<Long> geoadd(String key, double longitude, double latitude, Object member) {
      return null;
    }

    @Override
    public RedisFuture<Long> geoadd(String key, Object... lngLatMember) {
      return null;
    }

    @Override
    public RedisFuture<List<Value<String>>> geohash(String key, Object... members) {
      return null;
    }

    @Override
    public RedisFuture<Set<Object>> georadius(
      String key, double longitude, double latitude, double distance, GeoArgs.Unit unit) {
      return null;
    }

    @Override
    public RedisFuture<List<GeoWithin<Object>>> georadius(
      String key,
      double longitude,
      double latitude,
      double distance,
      GeoArgs.Unit unit,
      GeoArgs geoArgs) {
      return null;
    }

    @Override
    public RedisFuture<Long> georadius(
      String key,
      double longitude,
      double latitude,
      double distance,
      GeoArgs.Unit unit,
      GeoRadiusStoreArgs<String> geoRadiusStoreArgs) {
      return null;
    }

    @Override
    public RedisFuture<Set<Object>> georadiusbymember(
      String key, Object member, double distance, GeoArgs.Unit unit) {
      return null;
    }

    @Override
    public RedisFuture<List<GeoWithin<Object>>> georadiusbymember(
      String key, Object member, double distance, GeoArgs.Unit unit, GeoArgs geoArgs) {
      return null;
    }

    @Override
    public RedisFuture<Long> georadiusbymember(
      String key,
      Object member,
      double distance,
      GeoArgs.Unit unit,
      GeoRadiusStoreArgs<String> geoRadiusStoreArgs) {
      return null;
    }

    @Override
    public RedisFuture<List<GeoCoordinates>> geopos(String key, Object... members) {
      return null;
    }

    @Override
    public RedisFuture<Double> geodist(String key, Object from, Object to, GeoArgs.Unit unit) {
      return null;
    }

    @Override
    public RedisFuture<Long> pfadd(String key, Object... values) {
      return null;
    }

    @Override
    public RedisFuture<String> pfmerge(String destkey, String... sourcekeys) {
      return null;
    }

    @Override
    public RedisFuture<Long> pfcount(String... keys) {
      return null;
    }

    @Override
    public RedisFuture<Long> hdel(String key, String... fields) {
      return null;
    }

    @Override
    public RedisFuture<Boolean> hexists(String key, String field) {
      return null;
    }

    @Override
    public RedisFuture<Object> hget(String key, String field) {
      return null;
    }

    @Override
    public RedisFuture<Long> hincrby(String key, String field, long amount) {
      return null;
    }

    @Override
    public RedisFuture<Double> hincrbyfloat(String key, String field, double amount) {
      return null;
    }

    @Override
    public RedisFuture<Map<String, Object>> hgetall(String key) {
      return null;
    }

    @Override
    public RedisFuture<Long> hgetall(KeyValueStreamingChannel<String, Object> channel, String key) {
      return null;
    }

    @Override
    public RedisFuture<List<String>> hkeys(String key) {
      return null;
    }

    @Override
    public RedisFuture<Long> hkeys(KeyStreamingChannel<String> channel, String key) {
      return null;
    }

    @Override
    public RedisFuture<Long> hlen(String key) {
      return null;
    }

    @Override
    public RedisFuture<List<KeyValue<String, Object>>> hmget(String key, String... fields) {
      return null;
    }

    @Override
    public RedisFuture<Long> hmget(
      KeyValueStreamingChannel<String, Object> channel, String key, String... fields) {
      return null;
    }

    @Override
    public RedisFuture<String> hmset(String key, Map<String, Object> map) {
      return null;
    }

    @Override
    public RedisFuture<MapScanCursor<String, Object>> hscan(String key) {
      return null;
    }

    @Override
    public RedisFuture<MapScanCursor<String, Object>> hscan(String key, ScanArgs scanArgs) {
      return null;
    }

    @Override
    public RedisFuture<MapScanCursor<String, Object>> hscan(
      String key, ScanCursor scanCursor, ScanArgs scanArgs) {
      return null;
    }

    @Override
    public RedisFuture<MapScanCursor<String, Object>> hscan(String key, ScanCursor scanCursor) {
      return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> hscan(
      KeyValueStreamingChannel<String, Object> channel, String key) {
      return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> hscan(
      KeyValueStreamingChannel<String, Object> channel, String key, ScanArgs scanArgs) {
      return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> hscan(
      KeyValueStreamingChannel<String, Object> channel,
      String key,
      ScanCursor scanCursor,
      ScanArgs scanArgs) {
      return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> hscan(
      KeyValueStreamingChannel<String, Object> channel, String key, ScanCursor scanCursor) {
      return null;
    }

    @Override
    public RedisFuture<Boolean> hset(String key, String field, Object value) {
      return null;
    }

    @Override
    public RedisFuture<Boolean> hsetnx(String key, String field, Object value) {
      return null;
    }

    @Override
    public RedisFuture<Long> hstrlen(String key, String field) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> hvals(String key) {
      return null;
    }

    @Override
    public RedisFuture<Long> hvals(ValueStreamingChannel<Object> channel, String key) {
      return null;
    }

    @Override
    public RedisFuture<KeyValue<String, Object>> blpop(long timeout, String... keys) {
      return null;
    }

    @Override
    public RedisFuture<KeyValue<String, Object>> brpop(long timeout, String... keys) {
      return null;
    }

    @Override
    public RedisFuture<Object> brpoplpush(long timeout, String source, String destination) {
      return null;
    }

    @Override
    public RedisFuture<Object> lindex(String key, long index) {
      return null;
    }

    @Override
    public RedisFuture<Long> linsert(String key, boolean before, Object pivot, Object value) {
      return null;
    }

    @Override
    public RedisFuture<Long> llen(String key) {
      return null;
    }

    @Override
    public RedisFuture<Object> lpop(String key) {
      return null;
    }

    @Override
    public RedisFuture<Long> lpush(String key, Object... values) {
      return null;
    }

    @Override
    public RedisFuture<Long> lpushx(String key, Object... values) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> lrange(String key, long start, long stop) {
      return null;
    }

    @Override
    public RedisFuture<Long> lrange(
      ValueStreamingChannel<Object> channel, String key, long start, long stop) {
      return null;
    }

    @Override
    public RedisFuture<Long> lrem(String key, long count, Object value) {
      return null;
    }

    @Override
    public RedisFuture<String> lset(String key, long index, Object value) {
      return null;
    }

    @Override
    public RedisFuture<String> ltrim(String key, long start, long stop) {
      return null;
    }

    @Override
    public RedisFuture<Object> rpop(String key) {
      return null;
    }

    @Override
    public RedisFuture<Object> rpoplpush(String source, String destination) {
      return null;
    }

    @Override
    public RedisFuture<Long> rpush(String key, Object... values) {
      return null;
    }

    @Override
    public RedisFuture<Long> rpushx(String key, Object... values) {
      return null;
    }

    @Override
    public <T> RedisFuture<T> eval(String script, ScriptOutputType type, String... keys) {
      return null;
    }

    @Override
    public <T> RedisFuture<T> eval(
      String script, ScriptOutputType type, String[] keys, Object... values) {
      return null;
    }

    @Override
    public <T> RedisFuture<T> evalsha(String digest, ScriptOutputType type, String... keys) {
      return null;
    }

    @Override
    public <T> RedisFuture<T> evalsha(
      String digest, ScriptOutputType type, String[] keys, Object... values) {
      return null;
    }

    @Override
    public RedisFuture<List<Boolean>> scriptExists(String... digests) {
      return null;
    }

    @Override
    public RedisFuture<String> scriptFlush() {
      return null;
    }

    @Override
    public RedisFuture<String> scriptKill() {
      return null;
    }

    @Override
    public RedisFuture<String> scriptLoad(Object script) {
      return null;
    }

    @Override
    public String digest(Object script) {
      return null;
    }

    @Override
    public RedisFuture<String> bgrewriteaof() {
      return null;
    }

    @Override
    public RedisFuture<String> bgsave() {
      return null;
    }

    @Override
    public RedisFuture<String> clientGetname() {
      return null;
    }

    @Override
    public RedisFuture<String> clientSetname(String name) {
      return null;
    }

    @Override
    public RedisFuture<String> clientKill(String addr) {
      return null;
    }

    @Override
    public RedisFuture<Long> clientKill(KillArgs killArgs) {
      return null;
    }

    @Override
    public RedisFuture<Long> clientUnblock(long id, UnblockType type) {
      return null;
    }

    @Override
    public RedisFuture<String> clientPause(long timeout) {
      return null;
    }

    @Override
    public RedisFuture<String> clientList() {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> command() {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> commandInfo(String... commands) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> commandInfo(CommandType... commands) {
      return null;
    }

    @Override
    public RedisFuture<Long> commandCount() {
      return null;
    }

    @Override
    public RedisFuture<Map<String, String>> configGet(String parameter) {
      return null;
    }

    @Override
    public RedisFuture<String> configResetstat() {
      return null;
    }

    @Override
    public RedisFuture<String> configRewrite() {
      return null;
    }

    @Override
    public RedisFuture<String> configSet(String parameter, String value) {
      return null;
    }

    @Override
    public RedisFuture<Long> dbsize() {
      return null;
    }

    @Override
    public RedisFuture<String> debugCrashAndRecover(Long delay) {
      return null;
    }

    @Override
    public RedisFuture<String> debugHtstats(int db) {
      return null;
    }

    @Override
    public RedisFuture<String> debugObject(String key) {
      return null;
    }

    @Override
    public void debugOom() {}

    @Override
    public void debugSegfault() {}

    @Override
    public RedisFuture<String> debugReload() {
      return null;
    }

    @Override
    public RedisFuture<String> debugRestart(Long delay) {
      return null;
    }

    @Override
    public RedisFuture<String> debugSdslen(String key) {
      return null;
    }

    @Override
    public RedisFuture<String> flushall() {
      return null;
    }

    @Override
    public RedisFuture<String> flushallAsync() {
      return null;
    }

    @Override
    public RedisFuture<String> flushdb() {
      return null;
    }

    @Override
    public RedisFuture<String> flushdbAsync() {
      return null;
    }

    @Override
    public RedisFuture<String> info() {
      return null;
    }

    @Override
    public RedisFuture<String> info(String section) {
      return null;
    }

    @Override
    public RedisFuture<Date> lastsave() {
      return null;
    }

    @Override
    public RedisFuture<String> save() {
      return null;
    }

    @Override
    public void shutdown(boolean save) {}

    @Override
    public RedisFuture<String> slaveof(String host, int port) {
      return null;
    }

    @Override
    public RedisFuture<String> slaveofNoOne() {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> slowlogGet() {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> slowlogGet(int count) {
      return null;
    }

    @Override
    public RedisFuture<Long> slowlogLen() {
      return null;
    }

    @Override
    public RedisFuture<String> slowlogReset() {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> time() {
      return null;
    }

    @Override
    public RedisFuture<Long> sadd(String key, Object... members) {
      return null;
    }

    @Override
    public RedisFuture<Long> scard(String key) {
      return null;
    }

    @Override
    public RedisFuture<Set<Object>> sdiff(String... keys) {
      return null;
    }

    @Override
    public RedisFuture<Long> sdiff(ValueStreamingChannel<Object> channel, String... keys) {
      return null;
    }

    @Override
    public RedisFuture<Long> sdiffstore(String destination, String... keys) {
      return null;
    }

    @Override
    public RedisFuture<Set<Object>> sinter(String... keys) {
      return null;
    }

    @Override
    public RedisFuture<Long> sinter(ValueStreamingChannel<Object> channel, String... keys) {
      return null;
    }

    @Override
    public RedisFuture<Long> sinterstore(String destination, String... keys) {
      return null;
    }

    @Override
    public RedisFuture<Boolean> sismember(String key, Object member) {
      return null;
    }

    @Override
    public RedisFuture<Boolean> smove(String source, String destination, Object member) {
      return null;
    }

    @Override
    public RedisFuture<Set<Object>> smembers(String key) {
      return null;
    }

    @Override
    public RedisFuture<Long> smembers(ValueStreamingChannel<Object> channel, String key) {
      return null;
    }

    @Override
    public RedisFuture<Object> spop(String key) {
      return null;
    }

    @Override
    public RedisFuture<Set<Object>> spop(String key, long count) {
      return null;
    }

    @Override
    public RedisFuture<Object> srandmember(String key) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> srandmember(String key, long count) {
      return null;
    }

    @Override
    public RedisFuture<Long> srandmember(
      ValueStreamingChannel<Object> channel, String key, long count) {
      return null;
    }

    @Override
    public RedisFuture<Long> srem(String key, Object... members) {
      return null;
    }

    @Override
    public RedisFuture<Set<Object>> sunion(String... keys) {
      return null;
    }

    @Override
    public RedisFuture<Long> sunion(ValueStreamingChannel<Object> channel, String... keys) {
      return null;
    }

    @Override
    public RedisFuture<Long> sunionstore(String destination, String... keys) {
      return null;
    }

    @Override
    public RedisFuture<ValueScanCursor<Object>> sscan(String key) {
      return null;
    }

    @Override
    public RedisFuture<ValueScanCursor<Object>> sscan(String key, ScanArgs scanArgs) {
      return null;
    }

    @Override
    public RedisFuture<ValueScanCursor<Object>> sscan(
      String key, ScanCursor scanCursor, ScanArgs scanArgs) {
      return null;
    }

    @Override
    public RedisFuture<ValueScanCursor<Object>> sscan(String key, ScanCursor scanCursor) {
      return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> sscan(ValueStreamingChannel<Object> channel, String key) {
      return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> sscan(
      ValueStreamingChannel<Object> channel, String key, ScanArgs scanArgs) {
      return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> sscan(
      ValueStreamingChannel<Object> channel,
      String key,
      ScanCursor scanCursor,
      ScanArgs scanArgs) {
      return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> sscan(
      ValueStreamingChannel<Object> channel, String key, ScanCursor scanCursor) {
      return null;
    }

    @Override
    public RedisFuture<KeyValue<String, ScoredValue<Object>>> bzpopmin(
      long timeout, String... keys) {
      return null;
    }

    @Override
    public RedisFuture<KeyValue<String, ScoredValue<Object>>> bzpopmax(
      long timeout, String... keys) {
      return null;
    }

    @Override
    public RedisFuture<Long> zadd(String key, double score, Object member) {
      return null;
    }

    @Override
    public RedisFuture<Long> zadd(String key, Object... scoresAndValues) {
      return null;
    }

    @Override
    public RedisFuture<Long> zadd(String key, ScoredValue<Object>... scoredValues) {
      return null;
    }

    @Override
    public RedisFuture<Long> zadd(String key, ZAddArgs zAddArgs, double score, Object member) {
      return null;
    }

    @Override
    public RedisFuture<Long> zadd(String key, ZAddArgs zAddArgs, Object... scoresAndValues) {
      return null;
    }

    @Override
    public RedisFuture<Long> zadd(
      String key, ZAddArgs zAddArgs, ScoredValue<Object>... scoredValues) {
      return null;
    }

    @Override
    public RedisFuture<Double> zaddincr(String key, double score, Object member) {
      return null;
    }

    @Override
    public RedisFuture<Double> zaddincr(
      String key, ZAddArgs zAddArgs, double score, Object member) {
      return null;
    }

    @Override
    public RedisFuture<Long> zcard(String key) {
      return null;
    }

    @Override
    public RedisFuture<Long> zcount(String key, double min, double max) {
      return null;
    }

    @Override
    public RedisFuture<Long> zcount(String key, String min, String max) {
      return null;
    }

    @Override
    public RedisFuture<Long> zcount(String key, Range<? extends Number> range) {
      return null;
    }

    @Override
    public RedisFuture<Double> zincrby(String key, double amount, Object member) {
      return null;
    }

    @Override
    public RedisFuture<Long> zinterstore(String destination, String... keys) {
      return null;
    }

    @Override
    public RedisFuture<Long> zinterstore(String destination, ZStoreArgs storeArgs, String... keys) {
      return null;
    }

    @Override
    public RedisFuture<Long> zlexcount(String key, String min, String max) {
      return null;
    }

    @Override
    public RedisFuture<Long> zlexcount(String key, Range<?> range) {
      return null;
    }

    @Override
    public RedisFuture<ScoredValue<Object>> zpopmin(String key) {
      return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<Object>>> zpopmin(String key, long count) {
      return null;
    }

    @Override
    public RedisFuture<ScoredValue<Object>> zpopmax(String key) {
      return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<Object>>> zpopmax(String key, long count) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> zrange(String key, long start, long stop) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrange(
      ValueStreamingChannel<Object> channel, String key, long start, long stop) {
      return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<Object>>> zrangeWithScores(
      String key, long start, long stop) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrangeWithScores(
      ScoredValueStreamingChannel<Object> channel, String key, long start, long stop) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> zrangebylex(String key, String min, String max) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> zrangebylex(String key, Range<?> range) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> zrangebylex(
      String key, String min, String max, long offset, long count) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> zrangebylex(String key, Range<?> range, Limit limit) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> zrangebyscore(String key, double min, double max) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> zrangebyscore(String key, String min, String max) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> zrangebyscore(String key, Range<? extends Number> range) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> zrangebyscore(
      String key, double min, double max, long offset, long count) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> zrangebyscore(
      String key, String min, String max, long offset, long count) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> zrangebyscore(
      String key, Range<? extends Number> range, Limit limit) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrangebyscore(
      ValueStreamingChannel<Object> channel, String key, double min, double max) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrangebyscore(
      ValueStreamingChannel<Object> channel, String key, String min, String max) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrangebyscore(
      ValueStreamingChannel<Object> channel, String key, Range<? extends Number> range) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrangebyscore(
      ValueStreamingChannel<Object> channel,
      String key,
      double min,
      double max,
      long offset,
      long count) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrangebyscore(
      ValueStreamingChannel<Object> channel,
      String key,
      String min,
      String max,
      long offset,
      long count) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrangebyscore(
      ValueStreamingChannel<Object> channel,
      String key,
      Range<? extends Number> range,
      Limit limit) {
      return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<Object>>> zrangebyscoreWithScores(
      String key, double min, double max) {
      return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<Object>>> zrangebyscoreWithScores(
      String key, String min, String max) {
      return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<Object>>> zrangebyscoreWithScores(
      String key, Range<? extends Number> range) {
      return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<Object>>> zrangebyscoreWithScores(
      String key, double min, double max, long offset, long count) {
      return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<Object>>> zrangebyscoreWithScores(
      String key, String min, String max, long offset, long count) {
      return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<Object>>> zrangebyscoreWithScores(
      String key, Range<? extends Number> range, Limit limit) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrangebyscoreWithScores(
      ScoredValueStreamingChannel<Object> channel, String key, double min, double max) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrangebyscoreWithScores(
      ScoredValueStreamingChannel<Object> channel, String key, String min, String max) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrangebyscoreWithScores(
      ScoredValueStreamingChannel<Object> channel, String key, Range<? extends Number> range) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrangebyscoreWithScores(
      ScoredValueStreamingChannel<Object> channel,
      String key,
      double min,
      double max,
      long offset,
      long count) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrangebyscoreWithScores(
      ScoredValueStreamingChannel<Object> channel,
      String key,
      String min,
      String max,
      long offset,
      long count) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrangebyscoreWithScores(
      ScoredValueStreamingChannel<Object> channel,
      String key,
      Range<? extends Number> range,
      Limit limit) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrank(String key, Object member) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrem(String key, Object... members) {
      return null;
    }

    @Override
    public RedisFuture<Long> zremrangebylex(String key, String min, String max) {
      return null;
    }

    @Override
    public RedisFuture<Long> zremrangebylex(String key, Range<?> range) {
      return null;
    }

    @Override
    public RedisFuture<Long> zremrangebyrank(String key, long start, long stop) {
      return null;
    }

    @Override
    public RedisFuture<Long> zremrangebyscore(String key, double min, double max) {
      return null;
    }

    @Override
    public RedisFuture<Long> zremrangebyscore(String key, String min, String max) {
      return null;
    }

    @Override
    public RedisFuture<Long> zremrangebyscore(String key, Range<? extends Number> range) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> zrevrange(String key, long start, long stop) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrevrange(
      ValueStreamingChannel<Object> channel, String key, long start, long stop) {
      return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<Object>>> zrevrangeWithScores(
      String key, long start, long stop) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrevrangeWithScores(
      ScoredValueStreamingChannel<Object> channel, String key, long start, long stop) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> zrevrangebylex(String key, Range<?> range) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> zrevrangebylex(String key, Range<?> range, Limit limit) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> zrevrangebyscore(String key, double max, double min) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> zrevrangebyscore(String key, String max, String min) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> zrevrangebyscore(String key, Range<? extends Number> range) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> zrevrangebyscore(
      String key, double max, double min, long offset, long count) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> zrevrangebyscore(
      String key, String max, String min, long offset, long count) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> zrevrangebyscore(
      String key, Range<? extends Number> range, Limit limit) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrevrangebyscore(
      ValueStreamingChannel<Object> channel, String key, double max, double min) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrevrangebyscore(
      ValueStreamingChannel<Object> channel, String key, String max, String min) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrevrangebyscore(
      ValueStreamingChannel<Object> channel, String key, Range<? extends Number> range) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrevrangebyscore(
      ValueStreamingChannel<Object> channel,
      String key,
      double max,
      double min,
      long offset,
      long count) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrevrangebyscore(
      ValueStreamingChannel<Object> channel,
      String key,
      String max,
      String min,
      long offset,
      long count) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrevrangebyscore(
      ValueStreamingChannel<Object> channel,
      String key,
      Range<? extends Number> range,
      Limit limit) {
      return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<Object>>> zrevrangebyscoreWithScores(
      String key, double max, double min) {
      return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<Object>>> zrevrangebyscoreWithScores(
      String key, String max, String min) {
      return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<Object>>> zrevrangebyscoreWithScores(
      String key, Range<? extends Number> range) {
      return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<Object>>> zrevrangebyscoreWithScores(
      String key, double max, double min, long offset, long count) {
      return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<Object>>> zrevrangebyscoreWithScores(
      String key, String max, String min, long offset, long count) {
      return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<Object>>> zrevrangebyscoreWithScores(
      String key, Range<? extends Number> range, Limit limit) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrevrangebyscoreWithScores(
      ScoredValueStreamingChannel<Object> channel, String key, double max, double min) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrevrangebyscoreWithScores(
      ScoredValueStreamingChannel<Object> channel, String key, String max, String min) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrevrangebyscoreWithScores(
      ScoredValueStreamingChannel<Object> channel, String key, Range<? extends Number> range) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrevrangebyscoreWithScores(
      ScoredValueStreamingChannel<Object> channel,
      String key,
      double max,
      double min,
      long offset,
      long count) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrevrangebyscoreWithScores(
      ScoredValueStreamingChannel<Object> channel,
      String key,
      String max,
      String min,
      long offset,
      long count) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrevrangebyscoreWithScores(
      ScoredValueStreamingChannel<Object> channel,
      String key,
      Range<? extends Number> range,
      Limit limit) {
      return null;
    }

    @Override
    public RedisFuture<Long> zrevrank(String key, Object member) {
      return null;
    }

    @Override
    public RedisFuture<ScoredValueScanCursor<Object>> zscan(String key) {
      return null;
    }

    @Override
    public RedisFuture<ScoredValueScanCursor<Object>> zscan(String key, ScanArgs scanArgs) {
      return null;
    }

    @Override
    public RedisFuture<ScoredValueScanCursor<Object>> zscan(
      String key, ScanCursor scanCursor, ScanArgs scanArgs) {
      return null;
    }

    @Override
    public RedisFuture<ScoredValueScanCursor<Object>> zscan(String key, ScanCursor scanCursor) {
      return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> zscan(
      ScoredValueStreamingChannel<Object> channel, String key) {
      return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> zscan(
      ScoredValueStreamingChannel<Object> channel, String key, ScanArgs scanArgs) {
      return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> zscan(
      ScoredValueStreamingChannel<Object> channel,
      String key,
      ScanCursor scanCursor,
      ScanArgs scanArgs) {
      return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> zscan(
      ScoredValueStreamingChannel<Object> channel, String key, ScanCursor scanCursor) {
      return null;
    }

    @Override
    public RedisFuture<Double> zscore(String key, Object member) {
      return null;
    }

    @Override
    public RedisFuture<Long> zunionstore(String destination, String... keys) {
      return null;
    }

    @Override
    public RedisFuture<Long> zunionstore(String destination, ZStoreArgs storeArgs, String... keys) {
      return null;
    }

    @Override
    public RedisFuture<Long> xack(String key, String group, String... messageIds) {
      return null;
    }

    @Override
    public RedisFuture<String> xadd(String key, Map<String, Object> body) {
      return null;
    }

    @Override
    public RedisFuture<String> xadd(String key, XAddArgs args, Map<String, Object> body) {
      return null;
    }

    @Override
    public RedisFuture<String> xadd(String key, Object... keysAndValues) {
      return null;
    }

    @Override
    public RedisFuture<String> xadd(String key, XAddArgs args, Object... keysAndValues) {
      return null;
    }

    @Override
    public RedisFuture<List<StreamMessage<String, Object>>> xclaim(
      String key,
      io.lettuce.core.Consumer<String> consumer,
      long minIdleTime,
      String... messageIds) {
      return null;
    }

    @Override
    public RedisFuture<List<StreamMessage<String, Object>>> xclaim(
      String key,
      io.lettuce.core.Consumer<String> consumer,
      XClaimArgs args,
      String... messageIds) {
      return null;
    }

    @Override
    public RedisFuture<Long> xdel(String key, String... messageIds) {
      return null;
    }

    @Override
    public RedisFuture<String> xgroupCreate(
      XReadArgs.StreamOffset<String> streamOffset, String group) {
      return null;
    }

    @Override
    public RedisFuture<Boolean> xgroupDelconsumer(
      String key, io.lettuce.core.Consumer<String> consumer) {
      return null;
    }

    @Override
    public RedisFuture<Boolean> xgroupDestroy(String key, String group) {
      return null;
    }

    @Override
    public RedisFuture<String> xgroupSetid(
      XReadArgs.StreamOffset<String> streamOffset, String group) {
      return null;
    }

    @Override
    public RedisFuture<Long> xlen(String key) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> xpending(String key, String group) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> xpending(
      String key, String group, Range<String> range, Limit limit) {
      return null;
    }

    @Override
    public RedisFuture<List<Object>> xpending(
      String key, io.lettuce.core.Consumer<String> consumer, Range<String> range, Limit limit) {
      return null;
    }

    @Override
    public RedisFuture<List<StreamMessage<String, Object>>> xrange(
      String key, Range<String> range) {
      return null;
    }

    @Override
    public RedisFuture<List<StreamMessage<String, Object>>> xrange(
      String key, Range<String> range, Limit limit) {
      return null;
    }

    @Override
    public RedisFuture<List<StreamMessage<String, Object>>> xread(
      XReadArgs.StreamOffset<String>... streams) {
      return null;
    }

    @Override
    public RedisFuture<List<StreamMessage<String, Object>>> xread(
      XReadArgs args, XReadArgs.StreamOffset<String>... streams) {
      return null;
    }

    @Override
    public RedisFuture<List<StreamMessage<String, Object>>> xreadgroup(
      io.lettuce.core.Consumer<String> consumer, XReadArgs.StreamOffset<String>... streams) {
      return null;
    }

    @Override
    public RedisFuture<List<StreamMessage<String, Object>>> xreadgroup(
      io.lettuce.core.Consumer<String> consumer,
      XReadArgs args,
      XReadArgs.StreamOffset<String>... streams) {
      return null;
    }

    @Override
    public RedisFuture<List<StreamMessage<String, Object>>> xrevrange(
      String key, Range<String> range) {
      return null;
    }

    @Override
    public RedisFuture<List<StreamMessage<String, Object>>> xrevrange(
      String key, Range<String> range, Limit limit) {
      return null;
    }

    @Override
    public RedisFuture<Long> xtrim(String key, long count) {
      return null;
    }

    @Override
    public RedisFuture<Long> xtrim(String key, boolean approximateTrimming, long count) {
      return null;
    }

    @Override
    public RedisFuture<String> discard() {
      return null;
    }

    @Override
    public RedisFuture<TransactionResult> exec() {
      return null;
    }

    @Override
    public RedisFuture<String> multi() {
      return null;
    }

    @Override
    public RedisFuture<String> watch(String... keys) {
      return null;
    }

    @Override
    public RedisFuture<String> unwatch() {
      return null;
    }
  }

}
