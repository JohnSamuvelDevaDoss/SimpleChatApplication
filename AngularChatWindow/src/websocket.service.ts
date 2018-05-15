import { Injectable } from '@angular/core';
import * as Rx from 'rxjs/Rx';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import * as Stomp from 'stompjs';

@Injectable()
export class WebsocketService {
  private stompClient;

  constructor(public http: Http) { }

  private subject: Rx.Subject<MessageEvent>;

  public connect(url): Rx.Subject<MessageEvent> {
    if (!this.subject) {
      this.subject = this.create(url);
      console.log("Successfully connected: " + url);
    } 
    return this.subject;
  }

  private create(url): Rx.Subject<MessageEvent> {
    let ws = new WebSocket(url);

    let observable = Rx.Observable.create(
	(obs: Rx.Observer<MessageEvent>) => {
		ws.onmessage = obs.next.bind(obs);
		ws.onerror = obs.error.bind(obs);
		ws.onclose = obs.complete.bind(obs);
		return ws.close.bind(ws);
	})
let observer = {
		next: (data: Object) => {
			if (ws.readyState === WebSocket.OPEN) {
        this.http.get("http://localhost:8080/send",{}).subscribe(data=>{
          console.log(data['_body']); 
          //ws.send(JSON.stringify(data));
        }); 
        this.stompClient = Stomp.over(ws);
        this.stompClient.subscribe('http://localhost:8080/socket/websocketapp/send',data=>{
          console.log("response data"+data);
        })
        console.log(ws.url)
         
			}
    }
    //console.log(observer);
  }
 // console.log(observer);
	return Rx.Subject.create(observer, observable);
  }

}