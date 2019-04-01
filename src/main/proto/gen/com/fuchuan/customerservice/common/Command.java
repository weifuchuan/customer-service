// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: customer-service.proto

package com.fuchuan.customerservice.common;

/**
 * Protobuf enum {@code com.fuchuan.customerservice.common.Command}
 */
public enum Command
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>COMMAND_UNKNOW = 0;</code>
   */
  COMMAND_UNKNOW(0),
  /**
   * <pre>
   * 握手请求，含http的websocket握手请求
   * </pre>
   *
   * <code>COMMAND_HANDSHAKE_REQ = 1;</code>
   */
  COMMAND_HANDSHAKE_REQ(1),
  /**
   * <pre>
   * 握手响应，含http的websocket握手响应
   * </pre>
   *
   * <code>COMMAND_HANDSHAKE_RESP = 2;</code>
   */
  COMMAND_HANDSHAKE_RESP(2),
  /**
   * <pre>
   * 鉴权请求
   * </pre>
   *
   * <code>COMMAND_AUTH_REQ = 3;</code>
   */
  COMMAND_AUTH_REQ(3),
  /**
   * <pre>
   *  鉴权响应
   * </pre>
   *
   * <code>COMMAND_AUTH_RESP = 4;</code>
   */
  COMMAND_AUTH_RESP(4),
  /**
   * <pre>
   * 申请进入群组
   * </pre>
   *
   * <code>COMMAND_JOIN_GROUP_REQ = 5;</code>
   */
  COMMAND_JOIN_GROUP_REQ(5),
  /**
   * <pre>
   * 申请进入群组响应
   * </pre>
   *
   * <code>COMMAND_JOIN_GROUP_RESP = 6;</code>
   */
  COMMAND_JOIN_GROUP_RESP(6),
  /**
   * <pre>
   * 进入群组通知
   * </pre>
   *
   * <code>COMMAND_JOIN_GROUP_NOTIFY_RESP = 7;</code>
   */
  COMMAND_JOIN_GROUP_NOTIFY_RESP(7),
  /**
   * <pre>
   * 退出群组通知
   * </pre>
   *
   * <code>COMMAND_EXIT_GROUP_NOTIFY_RESP = 8;</code>
   */
  COMMAND_EXIT_GROUP_NOTIFY_RESP(8),
  /**
   * <pre>
   * 聊天请求
   * </pre>
   *
   * <code>COMMAND_CHAT_REQ = 9;</code>
   */
  COMMAND_CHAT_REQ(9),
  /**
   * <pre>
   * 聊天响应
   * </pre>
   *
   * <code>COMMAND_CHAT_RESP = 10;</code>
   */
  COMMAND_CHAT_RESP(10),
  /**
   * <pre>
   * 开播请求
   * </pre>
   *
   * <code>COMMAND_START_SHOW_REQ = 11;</code>
   */
  COMMAND_START_SHOW_REQ(11),
  /**
   * <pre>
   * 开播响应
   * </pre>
   *
   * <code>COMMAND_START_SHOW_RESP = 12;</code>
   */
  COMMAND_START_SHOW_RESP(12),
  /**
   * <pre>
   * 停播请求
   * </pre>
   *
   * <code>COMMAND_END_SHOW_REQ = 13;</code>
   */
  COMMAND_END_SHOW_REQ(13),
  /**
   * <pre>
   * 停播通知
   * </pre>
   *
   * <code>COMMAND_END_SHOW_NOTIFY_RESP = 14;</code>
   */
  COMMAND_END_SHOW_NOTIFY_RESP(14),
  /**
   * <pre>
   * 心跳请求
   * </pre>
   *
   * <code>COMMAND_HEARTBEAT_REQ = 15;</code>
   */
  COMMAND_HEARTBEAT_REQ(15),
  /**
   * <pre>
   * 心跳响应
   * </pre>
   *
   * <code>COMMAND_HEARTBEAT_RESP = 16;</code>
   */
  COMMAND_HEARTBEAT_RESP(16),
  /**
   * <pre>
   * 关闭请求
   * </pre>
   *
   * <code>COMMAND_CLOSE_REQ = 17;</code>
   */
  COMMAND_CLOSE_REQ(17),
  /**
   * <pre>
   * 分页请求Client列表
   * </pre>
   *
   * <code>COMMAND_CLIENT_PAGE_REQ = 18;</code>
   */
  COMMAND_CLIENT_PAGE_REQ(18),
  /**
   * <pre>
   * 返回Client列表
   * </pre>
   *
   * <code>COMMAND_CLIENT_PAGE_RESP = 19;</code>
   */
  COMMAND_CLIENT_PAGE_RESP(19),
  /**
   * <pre>
   * 登录请求
   * </pre>
   *
   * <code>COMMAND_LOGIN_REQ = 20;</code>
   */
  COMMAND_LOGIN_REQ(20),
  /**
   * <pre>
   * 登录响应
   * </pre>
   *
   * <code>COMMAND_LOGIN_RESP = 21;</code>
   */
  COMMAND_LOGIN_RESP(21),
  /**
   * <pre>
   * 发出撤消消息指令(管理员可以撤消所有人的消息，自己可以撤消自己的消息)
   * </pre>
   *
   * <code>COMMAND_CANCEL_MSG_REQ = 22;</code>
   */
  COMMAND_CANCEL_MSG_REQ(22),
  /**
   * <pre>
   * 收到撤消消息指令
   * </pre>
   *
   * <code>COMMAND_CANCEL_MSG_RESP = 23;</code>
   */
  COMMAND_CANCEL_MSG_RESP(23),
  /**
   * <pre>
   * 有响应的请求
   * </pre>
   *
   * <code>COMMAND_CALL_REQ = 24;</code>
   */
  COMMAND_CALL_REQ(24),
  /**
   * <pre>
   * 有响应的请求的响应
   * </pre>
   *
   * <code>COMMAND_CALL_RESP = 25;</code>
   */
  COMMAND_CALL_RESP(25),
  /**
   * <pre>
   * 提醒 推送
   * </pre>
   *
   * <code>COMMAND_REMIND_PUSH = 26;</code>
   */
  COMMAND_REMIND_PUSH(26),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>COMMAND_UNKNOW = 0;</code>
   */
  public static final int COMMAND_UNKNOW_VALUE = 0;
  /**
   * <pre>
   * 握手请求，含http的websocket握手请求
   * </pre>
   *
   * <code>COMMAND_HANDSHAKE_REQ = 1;</code>
   */
  public static final int COMMAND_HANDSHAKE_REQ_VALUE = 1;
  /**
   * <pre>
   * 握手响应，含http的websocket握手响应
   * </pre>
   *
   * <code>COMMAND_HANDSHAKE_RESP = 2;</code>
   */
  public static final int COMMAND_HANDSHAKE_RESP_VALUE = 2;
  /**
   * <pre>
   * 鉴权请求
   * </pre>
   *
   * <code>COMMAND_AUTH_REQ = 3;</code>
   */
  public static final int COMMAND_AUTH_REQ_VALUE = 3;
  /**
   * <pre>
   *  鉴权响应
   * </pre>
   *
   * <code>COMMAND_AUTH_RESP = 4;</code>
   */
  public static final int COMMAND_AUTH_RESP_VALUE = 4;
  /**
   * <pre>
   * 申请进入群组
   * </pre>
   *
   * <code>COMMAND_JOIN_GROUP_REQ = 5;</code>
   */
  public static final int COMMAND_JOIN_GROUP_REQ_VALUE = 5;
  /**
   * <pre>
   * 申请进入群组响应
   * </pre>
   *
   * <code>COMMAND_JOIN_GROUP_RESP = 6;</code>
   */
  public static final int COMMAND_JOIN_GROUP_RESP_VALUE = 6;
  /**
   * <pre>
   * 进入群组通知
   * </pre>
   *
   * <code>COMMAND_JOIN_GROUP_NOTIFY_RESP = 7;</code>
   */
  public static final int COMMAND_JOIN_GROUP_NOTIFY_RESP_VALUE = 7;
  /**
   * <pre>
   * 退出群组通知
   * </pre>
   *
   * <code>COMMAND_EXIT_GROUP_NOTIFY_RESP = 8;</code>
   */
  public static final int COMMAND_EXIT_GROUP_NOTIFY_RESP_VALUE = 8;
  /**
   * <pre>
   * 聊天请求
   * </pre>
   *
   * <code>COMMAND_CHAT_REQ = 9;</code>
   */
  public static final int COMMAND_CHAT_REQ_VALUE = 9;
  /**
   * <pre>
   * 聊天响应
   * </pre>
   *
   * <code>COMMAND_CHAT_RESP = 10;</code>
   */
  public static final int COMMAND_CHAT_RESP_VALUE = 10;
  /**
   * <pre>
   * 开播请求
   * </pre>
   *
   * <code>COMMAND_START_SHOW_REQ = 11;</code>
   */
  public static final int COMMAND_START_SHOW_REQ_VALUE = 11;
  /**
   * <pre>
   * 开播响应
   * </pre>
   *
   * <code>COMMAND_START_SHOW_RESP = 12;</code>
   */
  public static final int COMMAND_START_SHOW_RESP_VALUE = 12;
  /**
   * <pre>
   * 停播请求
   * </pre>
   *
   * <code>COMMAND_END_SHOW_REQ = 13;</code>
   */
  public static final int COMMAND_END_SHOW_REQ_VALUE = 13;
  /**
   * <pre>
   * 停播通知
   * </pre>
   *
   * <code>COMMAND_END_SHOW_NOTIFY_RESP = 14;</code>
   */
  public static final int COMMAND_END_SHOW_NOTIFY_RESP_VALUE = 14;
  /**
   * <pre>
   * 心跳请求
   * </pre>
   *
   * <code>COMMAND_HEARTBEAT_REQ = 15;</code>
   */
  public static final int COMMAND_HEARTBEAT_REQ_VALUE = 15;
  /**
   * <pre>
   * 心跳响应
   * </pre>
   *
   * <code>COMMAND_HEARTBEAT_RESP = 16;</code>
   */
  public static final int COMMAND_HEARTBEAT_RESP_VALUE = 16;
  /**
   * <pre>
   * 关闭请求
   * </pre>
   *
   * <code>COMMAND_CLOSE_REQ = 17;</code>
   */
  public static final int COMMAND_CLOSE_REQ_VALUE = 17;
  /**
   * <pre>
   * 分页请求Client列表
   * </pre>
   *
   * <code>COMMAND_CLIENT_PAGE_REQ = 18;</code>
   */
  public static final int COMMAND_CLIENT_PAGE_REQ_VALUE = 18;
  /**
   * <pre>
   * 返回Client列表
   * </pre>
   *
   * <code>COMMAND_CLIENT_PAGE_RESP = 19;</code>
   */
  public static final int COMMAND_CLIENT_PAGE_RESP_VALUE = 19;
  /**
   * <pre>
   * 登录请求
   * </pre>
   *
   * <code>COMMAND_LOGIN_REQ = 20;</code>
   */
  public static final int COMMAND_LOGIN_REQ_VALUE = 20;
  /**
   * <pre>
   * 登录响应
   * </pre>
   *
   * <code>COMMAND_LOGIN_RESP = 21;</code>
   */
  public static final int COMMAND_LOGIN_RESP_VALUE = 21;
  /**
   * <pre>
   * 发出撤消消息指令(管理员可以撤消所有人的消息，自己可以撤消自己的消息)
   * </pre>
   *
   * <code>COMMAND_CANCEL_MSG_REQ = 22;</code>
   */
  public static final int COMMAND_CANCEL_MSG_REQ_VALUE = 22;
  /**
   * <pre>
   * 收到撤消消息指令
   * </pre>
   *
   * <code>COMMAND_CANCEL_MSG_RESP = 23;</code>
   */
  public static final int COMMAND_CANCEL_MSG_RESP_VALUE = 23;
  /**
   * <pre>
   * 有响应的请求
   * </pre>
   *
   * <code>COMMAND_CALL_REQ = 24;</code>
   */
  public static final int COMMAND_CALL_REQ_VALUE = 24;
  /**
   * <pre>
   * 有响应的请求的响应
   * </pre>
   *
   * <code>COMMAND_CALL_RESP = 25;</code>
   */
  public static final int COMMAND_CALL_RESP_VALUE = 25;
  /**
   * <pre>
   * 提醒 推送
   * </pre>
   *
   * <code>COMMAND_REMIND_PUSH = 26;</code>
   */
  public static final int COMMAND_REMIND_PUSH_VALUE = 26;


  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static Command valueOf(int value) {
    return forNumber(value);
  }

  public static Command forNumber(int value) {
    switch (value) {
      case 0: return COMMAND_UNKNOW;
      case 1: return COMMAND_HANDSHAKE_REQ;
      case 2: return COMMAND_HANDSHAKE_RESP;
      case 3: return COMMAND_AUTH_REQ;
      case 4: return COMMAND_AUTH_RESP;
      case 5: return COMMAND_JOIN_GROUP_REQ;
      case 6: return COMMAND_JOIN_GROUP_RESP;
      case 7: return COMMAND_JOIN_GROUP_NOTIFY_RESP;
      case 8: return COMMAND_EXIT_GROUP_NOTIFY_RESP;
      case 9: return COMMAND_CHAT_REQ;
      case 10: return COMMAND_CHAT_RESP;
      case 11: return COMMAND_START_SHOW_REQ;
      case 12: return COMMAND_START_SHOW_RESP;
      case 13: return COMMAND_END_SHOW_REQ;
      case 14: return COMMAND_END_SHOW_NOTIFY_RESP;
      case 15: return COMMAND_HEARTBEAT_REQ;
      case 16: return COMMAND_HEARTBEAT_RESP;
      case 17: return COMMAND_CLOSE_REQ;
      case 18: return COMMAND_CLIENT_PAGE_REQ;
      case 19: return COMMAND_CLIENT_PAGE_RESP;
      case 20: return COMMAND_LOGIN_REQ;
      case 21: return COMMAND_LOGIN_RESP;
      case 22: return COMMAND_CANCEL_MSG_REQ;
      case 23: return COMMAND_CANCEL_MSG_RESP;
      case 24: return COMMAND_CALL_REQ;
      case 25: return COMMAND_CALL_RESP;
      case 26: return COMMAND_REMIND_PUSH;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<Command>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      Command> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<Command>() {
          public Command findValueByNumber(int number) {
            return Command.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return com.fuchuan.customerservice.common.CustomerServiceGrpc.getDescriptor().getEnumTypes().get(0);
  }

  private static final Command[] VALUES = values();

  public static Command valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private Command(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:com.fuchuan.customerservice.common.Command)
}

