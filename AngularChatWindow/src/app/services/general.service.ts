import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs/Rx';
import { Http,Headers, RequestOptions,Response} from '@angular/http';
import { HttpClient, HttpHeaders,  } from '@angular/common/http';
import { User } from '../models/user';
import { Conversation } from '../models/conversation';
import { ChatService } from '../../chat.service';


@Injectable()
export class GeneralService{

    public host:string = this.chatService.generalIp;
    public userListUrl:string=this.host+"/getusers";
    public createConversationUrl:string=this.host+"/createconversations";
    public crteateSessionUrl:string =this.host+"/createsession";
    public getconversationsUrl:string = this.host+"/getconversations"

    constructor(public http:HttpClient,public chatService:ChatService){
    }

    getConversation():Observable<Conversation[]>{
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json');
    
    return this.http.post <Conversation[]>(this.getconversationsUrl,{user:this.chatService.userNmae},{headers:headers});
    }

    getUserList():Observable<User[]>{
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');
    return this.http.get <User[]>(this.userListUrl,{headers});
    }

    createConversation(users):Observable<Conversation[]>{
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json');
    
    return this.http.post <Conversation[]>(this.createConversationUrl,users,{headers:headers});
    }

    crteateSessionId(){
    let headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');
    
    return this.http.post (this.crteateSessionUrl,{user:this.chatService.userNmae},{responseType: 'text'});
    }
}