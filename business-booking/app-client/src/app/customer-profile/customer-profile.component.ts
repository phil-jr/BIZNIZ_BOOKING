import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from "@angular/router";
import { CustomerProfileService } from '../service/customer-profile.service';
import { SocketService } from '../service/socket.service';

@Component({
  selector: 'app-customer-profile',
  templateUrl: './customer-profile.component.html',
  styleUrls: ['./customer-profile.component.css']
})
export class CustomerProfileComponent implements OnInit {

  public customer = {
    username: ""
  }

  //public announcmentList = [];

  dataSource: MatTableDataSource <String> ;

  displayedColumns: string[] = ['message'];

  constructor(private route: ActivatedRoute, private cps: CustomerProfileService, private socketConn: SocketService) { }

  ngOnInit(): void {
    console.log("alkerjwlekr")

    // this.customer.username = this.route.snapshot.paramMap.get("customer");
    // this.cps.getCustomerProfile(this.customer.username, (data)=> {
    //   console.log(data);
    // });
    this.dataSource = new MatTableDataSource([]);

    this.socketConn.connectToSocket("", "customer", (data)=> {
      let dataObj = JSON.parse(data);
      if (dataObj.process == "announcement") {
        console.log(this.dataSource)
        this.dataSource.data = [dataObj.announcement, ...this.dataSource.data];
      }
    });
  }

}
