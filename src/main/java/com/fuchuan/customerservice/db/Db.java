package com.fuchuan.customerservice.db;

import com.jfinal.kit.HashKit;
import java.util.*;

public class Db {
  public static class K {
    // room key 聊天室id：一对一聊天，按成员的id自然序排序后连接起来hash取前几位，以保持不变性
    public static String roomKey(String id1, String id2) {
      if (id1.compareTo(id2) > 0) {
        String t = id1;
        id1 = id2;
        id2 = t;
      }
      String key = id1 + "-" + id2;
      return HashKit.md5(key).substring(0, 16);
    }

    // room key 聊天室id：群聊，群号
    public static String roomKey(String groupNumber) {
      return groupNumber;
    }

    // 随机生成一个群号
    public static String groupNumber() {
      String str = "" + new Date().toString() + Math.random();
      String hashed = HashKit.sha256(str);
      StringBuilder sb = new StringBuilder(10);
      for (int i = 0; i < hashed.length(); i++) {
        if ('0' <= hashed.charAt(i) && hashed.charAt(i) <= '9') {
          sb.append(hashed.charAt(i));
        }
        if (sb.length() == 10) {
          break;
        }
      }
      if (sb.length() < 10) {
        for (int i = sb.length(); i < 10; i++) {
          sb.append('0');
        }
      }
      if (sb.charAt(0) == '0') {
        sb.setCharAt(0, '1');
      }
      return sb.toString();
    }

    // account 已加入的 room | set
    public static String joinedRooms(Object accountId) {
      return "im:joined:" + accountId;
    }

    // room info | hash
    public static String roomInfo(Object roomKey) {
      return "im:room:info:" + roomKey;
    }

    // members of room | set
    public static String members(Object roomKey) {
      return "im:room:members:" + roomKey;
    }

    // message key list of room | list
    public static String messages(Object roomKey) {
      return "im:room:messages:" + roomKey;
    }

    // message detail | hash
    public static String message(Object msgKey) {
      return "im:room:message:" + msgKey;
    }

    // remind message key list of room | list
    public static String remind(Object roomKey, Object accountId) {
      return "im:room:remind:" + roomKey + ":" + accountId;
    }

    public static String onlineCount(Object id){
      return "im:onlineCount:"+id;
    }
  }
}
