import { User } from "./user";
import { Messages } from "./messages";

export class Conversation{
conversationId?:string;
active?:boolean;
initiatedBy?:number;
reciever?:number;
messages?:Messages[];
users?:User[]
}