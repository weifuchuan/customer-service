package com.fuchuan.customerservice.server.handler;

import com.fuchuan.customerservice.common.Command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HandlerForCommand {
  Command value();
}
