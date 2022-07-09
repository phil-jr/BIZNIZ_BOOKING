import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpOptionsService } from './http-options.service';

@Injectable({
  providedIn: 'root'
})
export class CustomerProfileService {

  constructor(private http: HttpClient, private httpOptionsService: HttpOptionsService) { }

  getCustomerProfile(customerUsername, returnFunc){
    let options;
    if(localStorage.getItem("customer-session") !== null){
      options = this.httpOptionsService.getOptions("customer-session");
    } else {
      options = this.httpOptionsService.getOptions("");
    }
    this.http.get<any>(`http://localhost:8080/api/customer/profile/${customerUsername}`, options).subscribe(returnFunc);
  }
  
}
