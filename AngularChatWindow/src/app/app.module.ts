import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {ReactiveFormsModule,FormsModule} from '@angular/forms'
import { AppComponent } from './app.component';
import { ChatService } from '../chat.service';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';
import { GeneralService } from './services/general.service';
import {FlyingHeroesPipe} from './pipes/htmlpipe';

@NgModule({
  declarations: [
    AppComponent,FlyingHeroesPipe
  ],
  imports: [
    BrowserModule,HttpModule,FormsModule,HttpClientModule,ReactiveFormsModule
  ],
  providers: [ChatService, GeneralService],
  bootstrap: [AppComponent]
})
export class AppModule { }
