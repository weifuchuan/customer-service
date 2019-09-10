package com.fuchuan.customerservice.common.model;

import java.util.HashMap;
import java.util.Map;

public class RoomInfoModel extends HashMap {
  
  // 群聊的id
  public RoomInfoModel setId(String id) {
    return set("id", id);
  }

  @Optional// 群聊的id
  public String getId() {
    return (String)get("id");
  }
  
  // 群聊的名字
  public RoomInfoModel setName(String name) {
    return set("name", name);
  }

  @Optional// 群聊的名字
  public String getName() {
    return (String)get("name");
  }
  
  
  public RoomInfoModel setRoomKey(String roomKey) {
    return set("roomKey", roomKey);
  }

  
  public String getRoomKey() {
    return (String)get("roomKey");
  }
  
  // 聊天室类型：1对1（1）、群聊（2）
  public RoomInfoModel setType(Integer type) {
    return set("type", type);
  }

  // 聊天室类型：1对1（1）、群聊（2）
  public Integer getType() {
    return (Integer)get("type");
  }
  

  public RoomInfoModel set(String key, Object val) {
    put(key, val);
    return this;
  }

  public static RoomInfoModel from(Map obj) {
      RoomInfoModel model = new RoomInfoModel();
      model.putAll(obj);
      return model;
  }
}
