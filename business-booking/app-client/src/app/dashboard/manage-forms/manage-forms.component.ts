import { Component, OnInit } from '@angular/core';
import { BusinessDashboardService } from 'src/app/service/business-dashboard.service';
import { SocketService } from 'src/app/service/socket.service';

@Component({
  selector: 'app-manage-forms',
  templateUrl: './manage-forms.component.html',
  styleUrls: ['./manage-forms.component.css']
})
export class ManageFormsComponent implements OnInit {

  forms: any[] = []; 
  constructor(private bds: BusinessDashboardService, private socketConn: SocketService) { }

  ngOnInit(): void {
    this.bds.getForms((data) =>{
      this.forms = data;
    })
  }

}
