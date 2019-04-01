export interface IPayload{

}

export interface ICallReqPayload extends IPayload {
    
    
    payload: string;
    
    
    action: string;
    
    
    id: string;
    
}

export interface ICallRespPayload extends IPayload {
    
    
    ret: string;
    
    
    id: string;
    
}

export interface IChatReqPayload extends IPayload {
    
    // 发送到 用户id || 群号
    to: string;
    
    // 聊天室类型：1对1（1）、群聊（2）
    type: number;
    
    // 内容
    content: string;
    
}

export interface IClearRemindReqPayload extends IPayload {
    
    
    roomKey: string;
    
}

export interface IHeartbeatPayload extends IPayload {
    
}

export interface IOnlineNotifySubscribeReqPayload extends IPayload {
    
    
    who: string[];
    
}

export interface IOnlineOfflinePushPayload extends IPayload {
    
    
    nickName: string;
    
    
    id: string;
    
    
    avatar: string;
    
}

export interface IRemindPushPayload extends IPayload {
    
    
    from: string;
    
    
    msgKey: string;
    
    
    sendAt: number;
    
    
    to: string;
    
    
    roomKey: string;
    
    // 消息内容
    content: string;
    
}
