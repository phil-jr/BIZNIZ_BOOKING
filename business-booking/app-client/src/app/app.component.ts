import { AfterContentInit, Component, OnInit, ViewChild } from '@angular/core';
import { BusinessProfileService } from './service/business-profile.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  public businessUsername = localStorage.getItem("business-username");
  public customerUsername = localStorage.getItem("customer-username");

  @ViewChild('sidenav') sidenav: any;

  ngAfterContentInit(){
    this.sidenav
  }

  ngOnInit(): void {
  }

  showSignIn(){
    return localStorage.getItem("business-session") == null && localStorage.getItem("customer-session") == null;
  }

  showBusiness(){
    return localStorage.getItem("business-session") != null;
  }

  
  showCustomer(){
    return localStorage.getItem("customer-session") != null;
  }


  signOut(){
    if(localStorage.getItem("business-session") != null){
      localStorage.removeItem("business-session");
    }

    if(localStorage.getItem("customer-session") != null){
      localStorage.removeItem("customer-session");
    }

    window.location.href = "/";

  }

  title = 'empty-app';
}
