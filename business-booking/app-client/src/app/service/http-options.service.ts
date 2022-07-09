import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class HttpOptionsService {

  constructor() { }

  public getOptions(localStorageKey){
    let httpOptions;
    switch(localStorageKey){
      case 'customer-session':
        httpOptions = {
          headers: new HttpHeaders({
            'Content-Type':  'application/json',
            'customer-session': localStorage.getItem(localStorageKey)
          })
        };
        break;
      case 'business-session':
        httpOptions = {
          headers: new HttpHeaders({
            'Content-Type':  'application/json',
            'business-session': localStorage.getItem(localStorageKey)
          })
        };
        break;  
      default:
        httpOptions = {
          headers: new HttpHeaders({
            'Content-Type':  'application/json'
          })
        };
        break;
    }
    return httpOptions;
  }

}
