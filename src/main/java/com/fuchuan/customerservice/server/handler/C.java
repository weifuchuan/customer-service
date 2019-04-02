package com.fuchuan.customerservice.server.handler;

import io.reactivex.subjects.PublishSubject;

import java.util.Map;

public class C {

  // except unmodifiableMap
  public static final PublishSubject<Map> bus = PublishSubject.create();

}
