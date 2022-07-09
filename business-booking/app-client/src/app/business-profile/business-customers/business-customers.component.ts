import { Component, Input, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { Customer } from 'src/app/model/cusotmer-model';
import { BusinessProfileService } from 'src/app/service/business-profile.service';
import { SocketService } from 'src/app/service/socket.service';

@Component({
  selector: 'business-customers',
  templateUrl: './business-customers.component.html',
  styleUrls: ['./business-customers.component.css']
})
export class BusinessCustomersComponent implements OnInit {

  @Input() business;

  //customers: Array<Customer> = [];

  displayedColumns: string[] = ['firstName', 'lastName', 'email', 'username'];

  dataSource: MatTableDataSource <Customer> ;

  constructor(private route: ActivatedRoute, private bps: BusinessProfileService, private socketConn: SocketService) { }

  ngOnInit(): void {
    var username = this.route.snapshot.paramMap.get("business");
    this.bps.getBusinessCustomers(username, (data) => {
      this.dataSource = new MatTableDataSource(data);
    });

    this.socketConn.connectToSocket("", "business", (data)=> {
      let dataObj = JSON.parse(data);
      if (dataObj.process == "follow") {
        let newData = this.dataSource.data;
        newData.unshift(dataObj.obj);
        this.dataSource.data = newData;
      } else if (dataObj.process == "unfollow") {
        this.dataSource.data = this.dataSource.data.filter(item => item.username != dataObj.obj.username);
        this.dataSource._updateChangeSubscription(); 
      }
    });

  }

}
