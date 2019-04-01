package com.fuchuan.customerservice.mock.http;

import cn.hutool.core.io.FileUtil;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.http.server.annotation.RequestPath;
import org.tio.http.server.util.Resps;

@RequestPath("/cs")
public class IndexController {
  @RequestPath("/customer")
  public HttpResponse customer(HttpRequest request) {
    String html = FileUtil.readString("webapp/customer.html", "utf-8");
    return Resps.html(request, html);
  }

  @RequestPath("/waiter")
  public HttpResponse waiter(HttpRequest request) {
    String html = FileUtil.readString("webapp/waiter.html", "utf-8");
    return Resps.html(request, html);
  }
}
