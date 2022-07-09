import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpOptionsService } from './http-options.service';

@Injectable({
  providedIn: 'root'
})
export class BusinessProfileService {

  constructor(private http: HttpClient, private httpOptionsService: HttpOptionsService) { }

  getBusinessProfile(businessUsername, returnFunc){
    let options;
    if(localStorage.getItem("business-session") !== null){
      options = this.httpOptionsService.getOptions("business-session");
    } else {
      options = this.httpOptionsService.getOptions("");
    }
    this.http.get<any>(`http://localhost:8080/api/business/profile/${businessUsername}`, options).subscribe(returnFunc);
  }

  getBusinessProfileBySessionId(returnFunc){
    let options;
    if(localStorage.getItem("business-session") !== null){
      options = this.httpOptionsService.getOptions("business-session");
      this.http.get<any>(`http://localhost:8080/api/business/profile`, options).subscribe(returnFunc);
    }
  }

  customerFollow(businessUsername, returnFunc){
    console.log(businessUsername)
    if(localStorage.getItem("customer-session") !== null){
      let payload = {"businessUsername": businessUsername}
      let options = this.httpOptionsService.getOptions("customer-session");
      this.http.post<any>(`http://localhost:8080/api/customer/businessFollow`, payload, options).subscribe(returnFunc);
    }
  }

  customerUnfollow(businessUsername, returnFunc){
    console.log(businessUsername)
    if(localStorage.getItem("customer-session") !== null){
      let payload = {"businessUsername": businessUsername}
      let options = this.httpOptionsService.getOptions("customer-session");
      this.http.post<any>(`http://localhost:8080/api/customer/businessUnfollow`, payload, options).subscribe(returnFunc);
    }
  }

  //Change this to be pageable or get first 20
  getBusinessCustomers(businessUsername, returnFunc){
    let options = this.httpOptionsService.getOptions("");
    this.http.get<any>(`http://localhost:8080/api/business/customers/${businessUsername}`, options).subscribe(returnFunc);
  }

  getIsCustomerFollowing(businessUsername, returnFunc){
    let options;
    if(localStorage.getItem("customer-session") !== null){
      options = this.httpOptionsService.getOptions("customer-session");
      this.http.get<any>(`http://localhost:8080/api/business/is-following/${businessUsername}`, options).subscribe(returnFunc);
    }
  }

}
