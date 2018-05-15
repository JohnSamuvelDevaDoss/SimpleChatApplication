import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs/Rx';
import { WebsocketService } from './websocket.service';
import { Conversation } from './app/models/conversation';
import { User } from './app/models/user';

const CHAT_URL = 'ws://localhost:8080/socket/websocket';

export interface Message {
	author: string,
	message: string
}

@Injectable()
export class ChatService {
	public conversationList:Conversation[]=[];
	public currentConversationId = "";
	userNmae:string ="";
	userNameId:number=0;
	public currentReciver:string="";
	public generalIp="http://192.168.12.174:8080";
	public user:User = {};
}

/* public messages: Subject<String>;

	constructor(wsService: WebsocketService) {
		this.messages = <Subject<String>>wsService
			.connect(CHAT_URL)
			.map((response: MessageEvent): String => {
        console.log("response"+ response);
				let data = JSON.parse(response.data);
				return data;
			});
	} */