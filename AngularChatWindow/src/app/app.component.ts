import { Component, OnInit, OnDestroy,ViewChild,AfterViewInit,ElementRef } from '@angular/core';
import * as $ from 'jquery';
import EmojiPicker from 'rm-emoji-picker/dist/EmojiPicker';
//import EmojiPicker from "rm-emoji-picker";
import { ChatService } from '../chat.service';
import {FormControl,ReactiveFormsModule,FormsModule,FormBuilder,FormControlName,FormGroup,Validators} from '@angular/forms';
import * as Stomp from 'stompjs';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import * as SockJS from 'sockjs-client';
import { Message } from './message';
import { GeneralService } from './services/general.service';
import { User } from './models/user';
import { Conversation } from './models/conversation';
import { ConversationList } from './models/conversationList';
import { Messages } from './models/messages';
import { Subscription } from 'rxjs';
import {FlyingHeroesPipe} from './pipes/htmlpipe';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [ ]
})
export class AppComponent implements OnInit,OnDestroy  {

  public picker:EmojiPicker;
	constructor(public https: Http, public chatService:ChatService, public generalSerive:GeneralService) {
     this.picker = new EmojiPicker({
      sheets: {
        apple   : "./../assets/sheet_apple_64_indexed_128.png",
        google  : './../assets/sheet_google_64_indexed_128.png',
        twitter : './../assets/sheet_twitter_64_indexed_128.png',
        emojione: './../assets/sheet_emojione_64_indexed_128.png'
    }, 
  });
  }
  public text:Messages={};
  private stompClient;
  public texts:Messages[]=[];
  public convey:string="";
  public userName:string="";
  public recieverName:string="";
  public usersList:User[]=[];
  public reciverList:User[]=[];
  public conversation:Conversation={};
  public messageTemplateboolean = false;
  public chatTexts:Messages[] = [];
  public subscription:Subscription;
  public emojitext:any;
  setCurrentReciver(reciver:User){
    this.chatService.currentReciver=reciver.displayName;
    this.recieverName = reciver.displayName;
    this.messageTemplateboolean=true;
    
    //** this is for getting current messages */
    this.currentUpdate();
    
  }
  currentUpdate(){
    this.generalSerive.getConversation().subscribe(data=>{
      console.log(data);
      if(data.length>0){
        console.log("got conversations");
        this.chatService.conversationList=data;
          this.messageTemplateboolean = true;
          console.log(this.chatService.currentConversationId);
          this.chatTexts = this.chatService.conversationList.filter(data=>
            data.conversationId == this.chatService.currentConversationId)[0].messages;
            console.log(this.chatTexts);
      }
    })
    this.findConversationId();
    this.chatTexts = this.chatService.conversationList.filter(data=>
      data.conversationId == this.chatService.currentConversationId)[0].messages;
      console.log(this.chatTexts);
  }
  validText(text) {
    if (text==this.chatService.userNmae) {
      return true;
    } else {
      return false;
    }
  }
  initializeWebSocketConnection(data){
    var socket = new SockJS('http://localhost:8080/socket');
    this.stompClient = Stomp.over(socket);
    
    //stompClient.debug = null;
    this.stompClient.connect({}, (frame) => {
        console.log('Connected: ' + frame);
        this.stompClient.subscribe('/message/id/'+data, result =>{
          //console.log("this is called "+result.body);
          console.log(JSON.parse(result.body).text);
          let chats:Messages={}; 
          chats = <Messages>(JSON.parse(result.body))
          console.log(chats);
          if(this.recieverName != chats.author && this.chatService.userNmae != chats.author){
            alert("you have unread message from "+chats.author);
          }
          if(chats.author!="" && chats.conversations==this.chatService.currentConversationId){
            this.chatTexts.push(chats);
        }
    });
    this.subscription = this.stompClient.subscribe('/message/id/'+data+"/conversations", result =>{
      //console.log("this is called "+result.body);
      console.log(JSON.parse(result.body));
      let chats:Conversation[]=[]; 
      chats = <Conversation[]>(JSON.parse(result.body))
      console.log(chats);
      if(chats[0].conversationId!=null && chats[0].conversationId!=null){
        this.chatService.currentConversationId=chats[0].conversationId;
        if(chats[0].initiatedBy != this.chatService.userNameId){
        alert("new conversation from "+chats[0].users[0].displayName);
      }
       this.recieverName = this.chatService.currentReciver;
       // this.chatService.currentReciver = this.recieverName;*/
       // this.recieverName = chats[0].users[0].displayName; 
        this.reciverList.push(chats[0].users.filter(data =>
        data.userId != this.chatService.userNameId)[0]);
    }
});
  });
  }
  sendMsg() {
    this.convey="";
  //this.someInput.nativeElement.value="";
  console.log(this.picker.getText());
  let headers = new Headers();
  headers.append('Content-Type', 'application/json');
  let text:Messages = {};
  text.author = this.chatService.userNmae;
  text.sendTo= this.chatService.currentReciver;
  this.findConversationId();
  text.conversations = this.chatService.currentConversationId;
  text.text = this.picker.getText();
  console.log(text);
  console.log(text.conversations);
    this.https.post("http://192.168.12.174:8080/send",text).subscribe(data=>{
      console.log("inside request "+data['_body']); 
    });
    $(document).ready(() => {
      $('#text-input').empty();
    });
  }

  getUserList(){
    this.generalSerive.getUserList().subscribe((data:User[])=>{
      console.log(data);
      for(let user of data){
        if(user.displayName==this.chatService.userNmae){
          this.chatService.user=user;
          this.chatService.userNameId=user.userId;
        }else{
          this.usersList.push(user);
          console.log("userList initiate"+this.usersList);
        }
      }
    })
  }
  createConversation(reciever:User){
    let users:User[]=[];
    users.push(this.chatService.user);
    users.push(reciever);
    console.log(users);
    if(this.recieverName=""){
    this.recieverName = reciever.displayName; 
  }
    this.generalSerive.createConversation(users).subscribe(data=>{
      console.log(data);
      })
      
  }

  createSessionId(){
    this.generalSerive.crteateSessionId().subscribe(data=>{
      console.log(data);
      
      this.initializeWebSocketConnection(data);
    })
  }
  getAllConversations(){
    this.generalSerive.getConversation().subscribe(data=>{
      console.log(data);
      if(data.length>0){
        console.log("got conversations");
        this.chatService.conversationList=data;
        for(let convo of data){
          this.reciverList.push(
          convo.users.filter(user=>
            user.displayName != this.chatService.userNmae
          )[0])
        }
        console.log(this.reciverList);
        if(this.reciverList.length>0){
          console.log("got reciever list");
          this.chatService.currentReciver=this.reciverList[0].displayName;
          this.recieverName = this.reciverList[0].displayName;
          console.log(this.chatService.currentReciver);
          this.messageTemplateboolean = true;
          this.findConversationId();
          console.log(this.chatService.currentConversationId);
          this.chatTexts = this.chatService.conversationList.filter(data=>
            data.conversationId == this.chatService.currentConversationId)[0].messages;
            console.log(this.chatTexts);
          alert("you got "+this.reciverList.length+" messages");
        }
        console.log("starting point reciver list"+this.reciverList);
      }
    })
  }
  findConversationId(){
    let sender:number = this.chatService.userNameId;
    let reciever:number = this.usersList.filter(user=> user.displayName==this.chatService.currentReciver)[0].userId;
    for(let obj of this.chatService.conversationList){
      if(obj.active==true && (sender==obj.initiatedBy || sender==obj.reciever) && (obj.reciever==reciever
      ||obj.initiatedBy==reciever)){
        this.chatService.currentConversationId=obj.conversationId;
        console.log("loop running once");
        }
      } 
  }
 
  ngOnInit(){
    var person = prompt("Please enter your name", "");
    if (person != null) {
      console.log(person);
      this.chatService.userNmae=person;
      this.userName = this.chatService.userNmae;
  }
   this.createSessionId();
    this.getUserList();
    this.getAllConversations();
    this.emojitext = EmojiPicker.render("😠hey");
      console.log(this.emojitext);
    $(document).ready(() => {

      const icon      = document.querySelector('.fa-smile-o');
      const container = document.getElementById('container');
      const input     = document.getElementById('text-input');
      
       
       /* this.picker = new EmojiPicker({
            sheets: {
              apple   : "./../assets/sheet_apple_64_indexed_128.png",
              google  : './../assets/sheet_google_64_indexed_128.png',
              twitter : './../assets/sheet_twitter_64_indexed_128.png',
              emojione: './../assets/sheet_emojione_64_indexed_128.png'
          }, 
          positioning : "autoplace" 
      }); */
      this.picker.listenOn(icon, container, input);
  
      /* setInterval(() => {
          console.log(picker.getText());
      }, 3000); */
  });

  }
  ngOnDestroy(){
    this.subscription.unsubscribe();
    this.stompClient.disconnect();
  }

}
