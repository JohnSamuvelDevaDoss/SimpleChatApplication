import { DomSanitizer } from '@angular/platform-browser'
import { Pipe, PipeTransform } from '@angular/core';
import EmojiPicker from 'rm-emoji-picker/dist/EmojiPicker';

@Pipe({ name: 'safehtml'})

export class FlyingHeroesPipe implements PipeTransform {
    constructor(private sanitized: DomSanitizer){}
    transform(text: string){
      return this.sanitized.bypassSecurityTrustHtml(EmojiPicker.render(text));
    }
  }