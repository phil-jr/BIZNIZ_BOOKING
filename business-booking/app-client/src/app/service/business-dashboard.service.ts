import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpOptionsService } from './http-options.service';

@Injectable({
  providedIn: 'root'
})
export class BusinessDashboardService {

  constructor(private http: HttpClient, private httpOptionsService: HttpOptionsService) { }

  getForms(returnFunc){
    let options;
    if(localStorage.getItem("business-session") !== null){
      options = this.httpOptionsService.getOptions("business-session");
      this.http.get<any>(`http://localhost:8080/api/forms/forms`, options).subscribe(returnFunc);
    }
  }
  
}
