import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Business } from '../model/business-model';
import { BusinessProfileService } from '../service/business-profile.service';

@Component({
  selector: 'app-business-profile',
  templateUrl: './business-profile.component.html',
  styleUrls: ['./business-profile.component.css']
})
export class BusinessProfileComponent implements OnInit {


  public business: Business = new Business();
  public isFollowing: boolean;

  constructor(private route: ActivatedRoute, private bps: BusinessProfileService) { }

  ngOnInit(): void {
    var username = this.route.snapshot.paramMap.get("business");
    this.bps.getBusinessProfile(username, (data)=> {
      console.log(data);
      this.business = data;
    });

    this.bps.getIsCustomerFollowing(username, (data) =>{
      this.isFollowing = data.value;
    });

  }

  follow(){
    if(!this.isFollowing){
      this.bps.customerFollow(this.business.username, (data)=>{
        this.isFollowing = !this.isFollowing;
      });
    } else {
      this.bps.customerUnfollow(this.business.username, (data)=>{
        this.isFollowing = !this.isFollowing;
      });
    }
  }

}
