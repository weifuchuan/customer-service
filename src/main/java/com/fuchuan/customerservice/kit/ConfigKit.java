package com.fuchuan.customerservice.kit;

import cn.hutool.core.util.ReflectUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ConfigKit {

  public static <T> T createConfigObject(Properties props, Class<T> clazz, String prefix) {

    Object configObject = Utils.newInstance(clazz);

    Field[] fields = ReflectUtil.getFields(clazz);
    for (Field field : fields) {
      if (Modifier.isStatic(field.getModifiers())) continue;
      String name = field.getName();
      String key = buildKey(prefix, name);
      String value = props.getProperty(key);

      try {
        if (Utils.isNotBlank(value)) {
          Object val = convert(field.getType(), value);
          field.setAccessible(true);
          field.set(configObject, val);
        }
      } catch (Throwable ex) {
      }
    }

    //    List<Method> setMethods = Utils.getClassSetMethods(clazz);
    //    if (setMethods != null) {
    //      for (Method method : setMethods) {
    //
    //        String key = buildKey(prefix, method);
    //        String value = props.getProperty(key);
    //
    //        try {
    //          if (Utils.isNotBlank(value)) {
    //            Object val = convert(method.getParameterTypes()[0], value);
    //            method.invoke(configObject, val);
    //          }
    //        } catch (Throwable ex) {
    //          ex.printStackTrace();
    //        }
    //      }
    //    }

    return (T) configObject;
  }

  private static String buildKey(String prefix, Method method) {
    String key = Utils.firstCharToLowerCase(method.getName().substring(3));
    if (Utils.isNotBlank(prefix)) {
      key = prefix.trim() + "." + key;
    }
    return key;
  }

  private static String buildKey(String prefix, String name) {
    String key = Utils.firstCharToLowerCase(name);
    if (Utils.isNotBlank(prefix)) {
      key = prefix.trim() + "." + key;
    }
    return key;
  }

  public static Object convert(Class<?> type, String s) {
    return Utils.convert(type, s);
  }

  public static class Utils {

    public static boolean isBlank(String string) {
      return string == null || string.trim().equals("");
    }

    public static boolean isNotBlank(String string) {
      return !isBlank(string);
    }

    public static <T> T newInstance(Class<T> clazz) {
      try {
        Constructor constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        return (T) constructor.newInstance();
      } catch (Exception e) {
        e.printStackTrace();
      }
      return null;
    }

    public static List<Method> getClassSetMethods(Class clazz) {
      List<Method> setMethods = new ArrayList<>();
      Method[] methods = clazz.getMethods();
      for (Method method : methods) {
        if (method.getName().startsWith("set")
            && method.getName().length() > 3
            && method.getParameterCount() == 1) {

          setMethods.add(method);
        }
      }
      return setMethods;
    }

    public static String firstCharToLowerCase(String str) {
      char firstChar = str.charAt(0);
      if (firstChar >= 'A' && firstChar <= 'Z') {
        char[] arr = str.toCharArray();
        arr[0] += ('a' - 'A');
        return new String(arr);
      }
      return str;
    }

    private static String rootClassPath;

    public static String getRootClassPath() {
      if (rootClassPath == null) {
        try {
          String path = getClassLoader().getResource("").toURI().getPath();
          rootClassPath = new File(path).getAbsolutePath();
        } catch (Exception e) {
          try {
            String path = Utils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            path = java.net.URLDecoder.decode(path, "UTF-8");
            if (path.endsWith(File.separator)) {
              path = path.substring(0, path.length() - 1);
            }
            rootClassPath = path;
          } catch (UnsupportedEncodingException e1) {
            throw new RuntimeException(e1);
          }
        }
      }
      return rootClassPath;
    }

    public static ClassLoader getClassLoader() {
      ClassLoader ret = Thread.currentThread().getContextClassLoader();
      return ret != null ? ret : Utils.class.getClassLoader();
    }

    public static void doNothing(Throwable ex) {}

    public static final Object convert(Class<?> type, String s) {

      if (type == String.class) {
        return s;
      }

      if (type == char[].class) {
        return s.toCharArray();
      }

      if (type == Character[].class) {
        Character[] ret = new Character[s.length()];
        for (int i = 0; i < s.length(); i++) {
          ret[i] = s.toCharArray()[i];
        }
        return ret;
      }

      if (type == char.class || type == Character.class) {
        return s.toCharArray().length > 0 ? s.toCharArray()[0] : null;
      }

      if (type == Integer.class || type == int.class) {
        return Integer.parseInt(s);
      } else if (type == Long.class || type == long.class) {
        return Long.parseLong(s);
      } else if (type == Double.class || type == double.class) {
        return Double.parseDouble(s);
      } else if (type == Float.class || type == float.class) {
        return Float.parseFloat(s);
      } else if (type == Boolean.class || type == boolean.class) {
        String value = s.toLowerCase();
        if ("1".equals(value) || "true".equals(value)) {
          return Boolean.TRUE;
        } else if ("0".equals(value) || "false".equals(value)) {
          return Boolean.FALSE;
        } else {
          throw new RuntimeException("Can not parse to boolean type of value: " + s);
        }
      } else if (type == java.math.BigDecimal.class) {
        return new java.math.BigDecimal(s);
      } else if (type == java.math.BigInteger.class) {
        return new java.math.BigInteger(s);
      } else if (type == byte[].class) {
        return s.getBytes();
      }

      throw new RuntimeException(
          type.getName() + " can not be converted, please use other type in your config class!");
    }
  }
}
