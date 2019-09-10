export interface IModel{

}

export interface IMessageDetailModel extends IModel {
    
    // 消息内容
    content: string;
    
    
    from: string;
    
    
    msgKey: string;
    
    
    sendAt: number;
    
    
    to: string;
    
}

export interface IRoomInfoModel extends IModel {
    
    // 群聊的id
    id?: string;
    
    // 群聊的名字
    name?: string;
    
    
    roomKey: string;
    
    // 聊天室类型：1对1（1）、群聊（2）
    type: number;
    
}
