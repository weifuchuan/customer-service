// package: com.fuchuan.customerservice.common
// file: customer-service.proto

import * as jspb from "google-protobuf";

export class AccountBaseInfo extends jspb.Message {
  getId(): string;
  setId(value: string): void;

  getNickname(): string;
  setNickname(value: string): void;

  getAvatar(): string;
  setAvatar(value: string): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): AccountBaseInfo.AsObject;
  static toObject(includeInstance: boolean, msg: AccountBaseInfo): AccountBaseInfo.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: AccountBaseInfo, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): AccountBaseInfo;
  static deserializeBinaryFromReader(message: AccountBaseInfo, reader: jspb.BinaryReader): AccountBaseInfo;
}

export namespace AccountBaseInfo {
  export type AsObject = {
    id: string,
    nickname: string,
    avatar: string,
  }
}

export class AccountBaseInfoReq extends jspb.Message {
  getId(): string;
  setId(value: string): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): AccountBaseInfoReq.AsObject;
  static toObject(includeInstance: boolean, msg: AccountBaseInfoReq): AccountBaseInfoReq.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: AccountBaseInfoReq, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): AccountBaseInfoReq;
  static deserializeBinaryFromReader(message: AccountBaseInfoReq, reader: jspb.BinaryReader): AccountBaseInfoReq;
}

export namespace AccountBaseInfoReq {
  export type AsObject = {
    id: string,
  }
}

export class AccountBaseInfoReply extends jspb.Message {
  getCode(): Code;
  setCode(value: Code): void;

  hasAccount(): boolean;
  clearAccount(): void;
  getAccount(): AccountBaseInfo | undefined;
  setAccount(value?: AccountBaseInfo): void;

  getRole(): Role;
  setRole(value: Role): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): AccountBaseInfoReply.AsObject;
  static toObject(includeInstance: boolean, msg: AccountBaseInfoReply): AccountBaseInfoReply.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: AccountBaseInfoReply, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): AccountBaseInfoReply;
  static deserializeBinaryFromReader(message: AccountBaseInfoReply, reader: jspb.BinaryReader): AccountBaseInfoReply;
}

export namespace AccountBaseInfoReply {
  export type AsObject = {
    code: Code,
    account?: AccountBaseInfo.AsObject,
    role: Role,
  }
}

export class FetchAccountListBaseInfoReq extends jspb.Message {
  clearIdList(): void;
  getIdList(): Array<string>;
  setIdList(value: Array<string>): void;
  addId(value: string, index?: number): string;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): FetchAccountListBaseInfoReq.AsObject;
  static toObject(includeInstance: boolean, msg: FetchAccountListBaseInfoReq): FetchAccountListBaseInfoReq.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: FetchAccountListBaseInfoReq, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): FetchAccountListBaseInfoReq;
  static deserializeBinaryFromReader(message: FetchAccountListBaseInfoReq, reader: jspb.BinaryReader): FetchAccountListBaseInfoReq;
}

export namespace FetchAccountListBaseInfoReq {
  export type AsObject = {
    idList: Array<string>,
  }
}

export class FetchAccountListBaseInfoReply extends jspb.Message {
  clearAccountList(): void;
  getAccountList(): Array<AccountBaseInfo>;
  setAccountList(value: Array<AccountBaseInfo>): void;
  addAccount(value?: AccountBaseInfo, index?: number): AccountBaseInfo;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): FetchAccountListBaseInfoReply.AsObject;
  static toObject(includeInstance: boolean, msg: FetchAccountListBaseInfoReply): FetchAccountListBaseInfoReply.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: FetchAccountListBaseInfoReply, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): FetchAccountListBaseInfoReply;
  static deserializeBinaryFromReader(message: FetchAccountListBaseInfoReply, reader: jspb.BinaryReader): FetchAccountListBaseInfoReply;
}

export namespace FetchAccountListBaseInfoReply {
  export type AsObject = {
    accountList: Array<AccountBaseInfo.AsObject>,
  }
}

export class Waiters extends jspb.Message {
  clearAccountList(): void;
  getAccountList(): Array<AccountBaseInfo>;
  setAccountList(value: Array<AccountBaseInfo>): void;
  addAccount(value?: AccountBaseInfo, index?: number): AccountBaseInfo;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): Waiters.AsObject;
  static toObject(includeInstance: boolean, msg: Waiters): Waiters.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: Waiters, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): Waiters;
  static deserializeBinaryFromReader(message: Waiters, reader: jspb.BinaryReader): Waiters;
}

export namespace Waiters {
  export type AsObject = {
    accountList: Array<AccountBaseInfo.AsObject>,
  }
}

export class WaitersReq extends jspb.Message {
  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): WaitersReq.AsObject;
  static toObject(includeInstance: boolean, msg: WaitersReq): WaitersReq.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: WaitersReq, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): WaitersReq;
  static deserializeBinaryFromReader(message: WaitersReq, reader: jspb.BinaryReader): WaitersReq;
}

export namespace WaitersReq {
  export type AsObject = {
  }
}

export enum Command {
  COMMAND_UNKNOW = 0,
  COMMAND_HANDSHAKE_REQ = 1,
  COMMAND_HANDSHAKE_RESP = 2,
  COMMAND_CHAT_REQ = 9,
  COMMAND_HEARTBEAT_REQ = 15,
  COMMAND_HEARTBEAT_RESP = 16,
  COMMAND_CALL_REQ = 24,
  COMMAND_CALL_RESP = 25,
  COMMAND_REMIND_PUSH = 26,
  COMMAND_ONLINE_PUSH = 28,
  COMMAND_OFFLINE_PUSH = 29,
  COMMAND_CLEAR_REMIND_REQ = 27,
  COMMAND_ONLINE_NOTIFY_SUBSCRIBE_REQ = 30,
}

export enum Code {
  OK = 0,
  FAIL = -1,
}

export enum Role {
  CUSTOMER = 0,
  WAITER = 1,
}

