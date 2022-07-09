import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import * as moment from 'moment';
import { FormMetaData } from '../model/form-metadata-model';
import { AppointmentFormService } from '../service/appointment-form.service';
import { SocketService } from '../service/socket.service';

@Component({
  selector: 'app-business-appointment-form',
  templateUrl: './business-appointment-form.component.html',
  styleUrls: ['./business-appointment-form.component.css']
})
export class BusinessAppointmentFormComponent implements OnInit {

  constructor(private route: ActivatedRoute, private afs: AppointmentFormService, private socketConn: SocketService) { }

  public formMetaData: FormMetaData = new FormMetaData();
  public formLinkName = "";
  public username = "";
  public inputData = [];
  public selectedDate = null;
  displayedColumns: string[] = ['available'];
  date = new FormControl(moment());
  clickedDates = [];
  dateData = [];
  myFilter;

  ngOnInit(): void {
    this.username = this.route.snapshot.paramMap.get("business");
    this.formLinkName = this.route.snapshot.paramMap.get("form");

    this.afs.getBusinessForm(this.username, this.formLinkName, data => {
      this.formMetaData = data;
    });

    this.afs.getBusinessFormSchedule(this.username, this.formLinkName, data => {
      this.dateData = data;
      this.setFilters();
    });
  }

  bookAppointment(){

    let payload = {
      "timeSlotBegin": this.clickedDates[this.selectedDate].timeSlotBegin,
      "timeSlotEnd": this.clickedDates[this.selectedDate].timeSlotEnd,
      "formLinkName": this.formLinkName,
      "business": this.username,
      formData: []
    }

    this.formMetaData.formInputData.forEach(item => {
      payload.formData.push({"inputName": item.name, "inputValue": this.inputData[item.placement] })
    });

    this.afs.bookApoointment(payload, data => {
      console.log(data);
    });
  }

  dateChanged(event){
    let selectedDay = event.value;
    this.clickedDates = this.dateData.filter(item => {
      let day = moment(item.timeSlotBegin.substr(0, item.timeSlotBegin.length-2), "yyyy-MM-DD HH:mm:ss");
      return day.format("yyyy-MM-DD") == selectedDay.format("yyyy-MM-DD");
    });
  }

  setFilters(){
    let testDates = [];
    this.dateData.forEach(item => {
      testDates.push(moment(item.timeSlotBegin.substr(0, item.timeSlotBegin.length-2), "yyyy-MM-DD HH:mm:ss"));
    });

    this.myFilter = (d: any): boolean => {
      return testDates.findIndex(testDate => d.format("yyyy-MM-DD") == testDate.format("yyyy-MM-DD")) >= 0;
    };
  }

  getTimeFormat(date){
    return moment(date.substr(0, date.length-2), "yyyy-MM-DD HH:mm:ss").format("h:mm A");
  }

}
