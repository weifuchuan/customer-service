package com.fuchuan.customerservice.kit;

import cn.hutool.core.util.ReflectUtil;

import java.lang.reflect.Field;

public class ReflectKit {
  public static void setProp(Object target,String key, Object value){
    try{
      Field field = ReflectUtil.getField(target.getClass(), key);
      field.setAccessible(true);
      field.set(target, value);
    }catch (Exception ex){

    }
  }
}
