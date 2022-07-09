import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SignInService {

  constructor(private http: HttpClient) { }


  signInCustomer(payload, returnFunc){
    this.http.post<any>('http://localhost:8080/api/customer/signin', payload).subscribe(returnFunc);
  }

  signInBusiness(payload, returnFunc){
    this.http.post<any>('http://localhost:8080/api/business/signin', payload).subscribe(returnFunc);
  }

}
