package com.fuchuan.customerservice.server.handler;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.alibaba.fastjson.JSON;
import com.fuchuan.customerservice.common.AccountBaseInfo;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class C {
  private static final Logger log = LoggerFactory.getLogger(C.class);

  public static final ConcurrentMap<String, ConcurrentHashSet<ChannelContext>>
      idToOnlineWaiterCtxSet = new ConcurrentHashMap<>();

  // except unmodifiableMap
  public static final PublishSubject<Map> bus = PublishSubject.create();

  static {
    bus.subscribe(
        kv -> {
          ChannelContext ctx = (ChannelContext) kv.get("ctx");
          log.info("bus next: type = {}, userid = {}", kv.get("type"), ctx.userid);
        });
  }
}
