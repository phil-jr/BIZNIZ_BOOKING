import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { CustomerProfileService } from '../service/customer-profile.service';

@Component({
  selector: 'app-customer-profile',
  templateUrl: './customer-profile.component.html',
  styleUrls: ['./customer-profile.component.css']
})
export class CustomerProfileComponent implements OnInit {

  public customer = {
    username: ""
  }

  constructor(private route: ActivatedRoute, private cps: CustomerProfileService) { }

  ngOnInit(): void {
    this.customer.username = this.route.snapshot.paramMap.get("customer");
    this.cps.getCustomerProfile(this.customer.username, (data)=> {
      console.log(data);
    });
  }

}
