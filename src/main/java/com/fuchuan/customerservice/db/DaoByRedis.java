package com.fuchuan.customerservice.db;

import com.fuchuan.customerservice.common.model.MessageDetailModel;
import com.fuchuan.customerservice.common.model.RoomInfoModel;
import com.fuchuan.customerservice.common.payload.ChatReqPayload;
import com.jfinal.kit.Kv;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import org.tio.utils.page.Page;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DaoByRedis implements IDao {

  public Kv sendChatMsg(ChatReqPayload payload, String from) throws Exception {
    Kv ret = Kv.create();
    //    RedisAsyncCommands<String, Object> async = R.async();
    //    async.setAutoFlushCommands(false);
    R.pipe(
        async -> {
          MessageDetailModel msg = null;
          RoomInfoModel room = null;
          if (payload.getType() == 1) {
            String to = payload.getTo();
            // save message
            String roomKey = Db.K.roomKey(from, to);
            msg =
                new MessageDetailModel()
                    .setMsgKey(nextId())
                    .setFrom(from)
                    .setTo(to)
                    .setSendAt(new Date().getTime())
                    .setContent(payload.getContent());
            R.setHash(async, Db.K.message(msg.getMsgKey()), msg);
            async.lpush(Db.K.messages(roomKey), msg.getMsgKey());
            // join room
            async.sadd(Db.K.joinedRooms(from), roomKey);
            async.sadd(Db.K.joinedRooms(payload.getTo()), roomKey);
            // save room info
            room = new RoomInfoModel().setType(1).setRoomKey(roomKey);
            R.setHash(async, Db.K.roomInfo(roomKey), room);
            async.sadd(Db.K.members(roomKey), from, payload.getTo());
            // add remind
            async.lpush(Db.K.remind(roomKey, to), msg.getMsgKey());
          } else {
            // TODO: 实现群聊
          }
          ret.set("msg", msg).set("room", room);
        });
    //    async.flushCommands();
    return ret;
  }

  @Override
  public void saveMessage(MessageDetailModel msg, String roomKey) throws Exception {
    R.pipe(
        async -> {
          R.setHash(async, Db.K.message(msg.getMsgKey()), msg);
          async.lpush(Db.K.messages(roomKey), msg.getMsgKey());
        });
  }

  @Override
  public void saveRoomInfo(RoomInfoModel room, String roomKey, Set<String> members)
      throws Exception {
    RedisCommands<String, Object> sync = R.sync();
    if (!sync.hexists(Db.K.roomInfo(roomKey), "roomKey")) {
      R.pipeNotRetAsync(
          async -> {
            R.setHash(async, Db.K.roomInfo(roomKey), room);
            async.sadd(Db.K.members(roomKey), members.toArray());
          });
    }
  }

  @Override
  public void addRemind(String msgKey, String roomKey, String accountId) throws Exception {
    R.sync().lpush(Db.K.remind(roomKey, accountId), msgKey);
  }

  @Override
  public void clearRemind(String roomKey, String accountId) throws Exception {
    R.async().del(Db.K.remind(roomKey, accountId));
  }

  @Override
  public List<Map> joinedRoomList(String id) throws Exception {
    RedisCommands<String, Object> sync = R.sync();
    List<Object> roomKeyList =
        sync.smembers(Db.K.joinedRooms(id)).stream().collect(Collectors.toList());
    List<Object> ret =
        R.pipe(
            async -> {
              roomKeyList.forEach(
                  roomKey -> {
                    async.llen(Db.K.remind(roomKey, id));
                    async.lindex(Db.K.messages(roomKey), 0);
                    async.hgetall(Db.K.roomInfo(roomKey));
                    async.smembers(Db.K.members(roomKey));
                  });
            });
    List<Long> remindCountList = new ArrayList<>();
    List<String> lastMsgKeyList = new ArrayList<>();
    List<RoomInfoModel> roomInfoList = new ArrayList<>();
    List<Set<Object>> roomMembersSet = new ArrayList<>();
    for (int i = 0; i < ret.size(); i++) {
      if ((i) % 4 == 0) {
        remindCountList.add((Long) ret.get(i));
      } else if ((i) % 4 == 1) {
        lastMsgKeyList.add((String) ret.get(i));
      } else if ((i) % 4 == 2) {
        roomInfoList.add(RoomInfoModel.from((Map) ret.get(i)));
      } else if ((i) % 4 == 3) {
        roomMembersSet.add((Set<Object>) ret.get(i));
      }
    }
    ret =
        R.pipe(
            async -> {
              for (String msgKey : lastMsgKeyList) {
                async.hgetall(Db.K.message(msgKey));
              }
            });
    List<MessageDetailModel> lastMsgList =
        ret.stream().map(msg -> MessageDetailModel.from((Map) msg)).collect(Collectors.toList());
    AtomicInteger i = new AtomicInteger(0);
    return roomInfoList.stream()
        .map(
            room -> {
              room.set("remindCount", remindCountList.get(i.get()));
              room.set("lastMsg", lastMsgList.get(i.get()));
              room.set("members", roomMembersSet.get(i.get()));
              i.getAndIncrement();
              return room;
            })
        .filter(room -> room.get("lastMsg") != null)
        .sorted(
            (_a, _b) -> {
              MessageDetailModel a = (MessageDetailModel) _a.get("lastMsg");
              MessageDetailModel b = (MessageDetailModel) _b.get("lastMsg");
              long sendAt1 = a.getSendAt() == null ? 0 : a.getSendAt();
              long sendAt2 = b.getSendAt() == null ? 0 : b.getSendAt();
              return Long.compare(sendAt2, sendAt1);
            })
        .collect(Collectors.toList());
  }

  @Override
  public void joinRoom(String id, String roomKey) throws Exception {
    R.sync().sadd(Db.K.joinedRooms(id), roomKey);
  }

  @Override
  public void joinRoom(Map<String, String> idToRoomKey) throws Exception {
    RedisAsyncCommands<String, Object> async = R.async();
    async.setAutoFlushCommands(false);
    idToRoomKey.forEach(
        (id, roomKey) -> {
          async.sadd(Db.K.joinedRooms(id), roomKey);
        });
    async.flushCommands();
  }

  @Override
  public RoomInfoModel getRoomInfo(String roomKey, String accountId) throws Exception {
    List<Object> ret =
        R.pipe(
            async -> {
              async.hgetall(Db.K.roomInfo(roomKey));
              async.llen(Db.K.remind(roomKey, accountId));
              async.lindex(Db.K.remind(roomKey, accountId), 0);
              async.smembers(Db.K.members(roomKey));
            });

    Map<String, Object> map = (Map<String, Object>) ret.get(0);
    Long remindCount = (Long) ret.get(1);
    String msgKey = (String) ret.get(2);
    Set<Object> members = (Set<Object>) ret.get(3);

    Map<String, Object> lastMsg = R.sync().hgetall(Db.K.message(msgKey));

    return RoomInfoModel.from(map)
        .set("remindCount", remindCount)
        .set("lastMsg", lastMsg)
        .set("members", members);
  }

  public Page<MessageDetailModel> messagePage(String roomKey, Integer pageNumber, Integer pageSize)
      throws Exception {
    RedisAsyncCommands<String, Object> async = R.async();
    async.setAutoFlushCommands(false);
    RedisFuture<Long> totalCount = async.llen(Db.K.messages(roomKey));
    RedisFuture<List<Object>> msgKeyList =
        async.lrange(
            Db.K.messages(roomKey), (pageNumber - 1) * pageSize, pageNumber * pageSize - 1);
    async.flushCommands();
    List<RedisFuture<Map<String, Object>>> msgList =
        msgKeyList.get().stream()
            .map(msgKey -> async.hgetall(Db.K.message(msgKey)))
            .collect(Collectors.toList());
    async.flushCommands();
    return new Page<>(
        msgList.stream()
            .map(
                msgF -> {
                  try {
                    return MessageDetailModel.from(msgF.get());
                  } catch (InterruptedException e) {
                    e.printStackTrace();
                  } catch (ExecutionException e) {
                    e.printStackTrace();
                  }
                  return (MessageDetailModel) null;
                })
            .collect(Collectors.toList()),
        pageNumber,
        pageSize,
        Math.toIntExact(totalCount.get()));
  }

  @Override
  public String nextId() {
    return R.nextId();
  }
}
