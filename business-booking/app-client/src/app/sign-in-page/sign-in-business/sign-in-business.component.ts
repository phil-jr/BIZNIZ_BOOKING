import { Component, OnInit } from '@angular/core';
import { SignInService } from 'src/app/service/sign-in.service';

@Component({
  selector: 'sign-in-business',
  templateUrl: './sign-in-business.component.html',
  styleUrls: ['./sign-in-business.component.css']
})
export class SignInBusinessComponent implements OnInit {

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
  signInBusiness(){
    if(this.signInObj.username != "" && this.signInObj.password != ""){
      this.signInService.signInBusiness(this.signInObj, data => {
        if(data.errors.length == 0){
          console.log(data);
          localStorage.setItem("business-session", data.sessionId);
          localStorage.setItem("business-username", this.signInObj.username);
          window.location.href = `http://localhost:8080/business/${data.username}`;
        } else {
          console.log(data.errors)
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
