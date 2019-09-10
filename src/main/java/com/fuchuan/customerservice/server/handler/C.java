package com.fuchuan.customerservice.server.handler;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class C {
  private static final Logger log = LoggerFactory.getLogger(C.class);

  // except unmodifiableMap
  public static final Subject<Map> bus = PublishSubject.<Map>create().toSerialized();

}
