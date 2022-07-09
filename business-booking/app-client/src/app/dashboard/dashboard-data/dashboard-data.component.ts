import { AfterContentInit, Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard-data',
  templateUrl: './dashboard-data.component.html',
  styleUrls: ['./dashboard-data.component.css']
})
export class DashboardDataComponent implements OnInit, AfterContentInit {

  data: any[] = [{
    "name": "Followers",
    "series": [
      {
      "name": "7 Days",
      "value": 8
      },
      {
        "name": "5 Days",
        "value": 20
      },
      {
        "name": "3 Days",
        "value": 9
      },
      {
        "name": "1 Day",
        "value": 1
      }
  ]
  }];

  dataOptions: any[] = [
    {"value": "followers", "viewValue": "Followers"},
    {"value": "appointments", "viewValue": "All Appointments"},
    {"value": "revenue", "viewValue": "Revenue"},
    {"value": "form1appnts", "viewValue": "Form 1 Appointments"}
  ]

  timeOptions: any[] = [
    {"value": "week", "viewValue": "Last 7 Days"},
    {"value": "month", "viewValue": "Last 30 Days"},
    {"value": "year", "viewValue": "Last Year"}
  ]

  view: any[] = [];
  legend: boolean = true;
  showLabels: boolean = true;
  animations: boolean = true;
  xAxis: boolean = true;
  yAxis: boolean = true;
  showYAxisLabel: boolean = true;
  showXAxisLabel: boolean = true;
  xAxisLabel: string = 'Time';
  yAxisLabel: string = this.dataOptions[0].viewValue;
  timeline: boolean = true;

  colorScheme = {
    domain: ['#CFC0BB']
  };

  onSelect(data): void {
    console.log('Item clicked', JSON.parse(JSON.stringify(data)));
  }

  onActivate(data): void {
    console.log('Activate', JSON.parse(JSON.stringify(data)));
  }

  onDeactivate(data): void {
    console.log('Deactivate', JSON.parse(JSON.stringify(data)));
  }

  setView(){
    var width = document.getElementById('chart-col').offsetWidth;
    console.log(width);
    let height = window.innerHeight;
    this.view = [width, height];
  }

  constructor() { }

  ngOnInit(): void {
  }

  ngAfterContentInit(): void {
    let that = this;
    window.onload = function(){
      that.setView();
  }
  }

}
