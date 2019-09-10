package com.fuchuan.customerservice.db;

import com.fuchuan.customerservice.common.model.MessageDetailModel;
import com.fuchuan.customerservice.common.model.RoomInfoModel;
import com.fuchuan.customerservice.common.payload.ChatReqPayload;
import com.jfinal.kit.Kv;
import org.tio.utils.page.Page;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public interface IDao {
  Kv sendChatMsg(ChatReqPayload payload, String from) throws Exception;

  void saveMessage(MessageDetailModel msg, String roomKey) throws Exception;

  void saveRoomInfo(RoomInfoModel room, String roomKey, Set<String> members) throws Exception;

  void addRemind(String msgKey, String roomKey,String accountId) throws Exception;

  void clearRemind(String roomKey,String accountId) throws Exception;

  List<Map> joinedRoomList(String id) throws Exception;

  void joinRoom(String id, String roomKey) throws Exception;

  void joinRoom(Map<String,String> idToRoomKey) throws Exception;

  RoomInfoModel getRoomInfo(String roomKey,String accountId) throws Exception;

  Page<MessageDetailModel> messagePage(String roomKey, Integer pageNumber, Integer pageSize)
    throws Exception;

  String nextId();

  int onlineCountForUserId(String userId);

  void incrOnlineCount(String userId);

  void decrOnlineCount(String userId);

  boolean isOnline(String userId);
}
