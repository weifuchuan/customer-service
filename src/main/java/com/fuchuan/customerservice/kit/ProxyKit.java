package com.fuchuan.customerservice.kit;

import io.lettuce.core.RedisAsyncCommandsImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;

public class ProxyKit {
  public static interface Interceptor {
    void intercept(Object ret, Method method, Object[] args);
  }

  public static <Target> Target intercept(Target target, Interceptor interceptor) {
    return (Target)
        Enhancer.create(
            target.getClass(),
            (MethodInterceptor)
                (o, method, args, methodProxy) -> {
                  Object ret = method.invoke(target, args);
                  interceptor.intercept(ret, method, args);
                  return ret;
                });
  }

  public static <Target> Target intercept(
      Target target, Class<? extends Target> clz, Interceptor interceptor) {
    return (Target)
        Enhancer.create(
            clz,
            (MethodInterceptor)
                (o, method, args, methodProxy) -> {
                  method =
                      target.getClass().getMethod(method.getName(), method.getParameterTypes());
                  Object ret = method.invoke(target, args);
                  interceptor.intercept(ret, method, args);
                  return ret;
                });
  }
}
