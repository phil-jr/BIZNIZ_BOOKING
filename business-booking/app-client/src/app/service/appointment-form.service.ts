import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpOptionsService } from './http-options.service';

@Injectable({
  providedIn: 'root'
})
export class AppointmentFormService {

  constructor(private http: HttpClient, private httpOptionsService: HttpOptionsService) { }

  getBusinessForm(business, formLinkName, returnFunc){
    let options = this.httpOptionsService.getOptions("");
    this.http.get<any>(`http://localhost:8080/api/forms/formMetadata/${business}/${formLinkName}`, options).subscribe(returnFunc);
  }

  getBusinessFormSchedule(business, formLinkName, returnFunc){
    let options = this.httpOptionsService.getOptions("");
    this.http.get<any>(`http://localhost:8080/api/forms/getAppointmentTimes/${business}/${formLinkName}`, options).subscribe(returnFunc);
  }

  bookApoointment(payload, returnFunc){
    let options = this.httpOptionsService.getOptions("customer-session");
    this.http.post<any>(`http://localhost:8080/api/forms/bookAppointment`, payload, options).subscribe(returnFunc);
  }

}
