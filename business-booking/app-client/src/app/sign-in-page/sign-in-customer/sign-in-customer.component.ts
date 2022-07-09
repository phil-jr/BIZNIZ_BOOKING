import { Component, OnInit } from '@angular/core';
import { SignInService } from 'src/app/service/sign-in.service';

@Component({
  selector: 'sign-in-customer',
  templateUrl: './sign-in-customer.component.html',
  styleUrls: ['./sign-in-customer.component.css']
})
export class SignInCustomerComponent implements OnInit {
  
  hidePass = true;

  signInObj = {
    username: "",
    password: ""
  }

  signInObjError = {
    username: false,
    password: false,
    usernameMsg: "",
    passwordMsg: ""
  }

  constructor(public signInService: SignInService) { }

  ngOnInit(): void {
  }

  //TODO add validators
  signInCustomer(){
    if(this.signInObj.username != "" && this.signInObj.password != ""){
      this.signInService.signInCustomer(this.signInObj, data => {
        if(data.errors.length == 0){
          console.log(data);
          localStorage.setItem("customer-session", data.sessionId);
          localStorage.setItem("customer-username", this.signInObj.username);
          window.location.href = `http://localhost:8080/customer/${data.username}`;
        } else {
          console.log(data.errors);
        }
      });
    } else {
      console.log(this.signInObjError);
      this.signInObjError.username = true;
      this.signInObjError.password = true;
      this.signInObjError.usernameMsg = "Username or Email is Missing";      
      this.signInObjError.passwordMsg = "Password is Missing";
    }
  }

}
