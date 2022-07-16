import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpOptionsService } from './http-options.service';

@Injectable({
  providedIn: 'root'
})
export class AnnouncementService {

  constructor(private http: HttpClient, private httpOptionsService: HttpOptionsService) { }

  postBusinessAnnouncement(payload, returnFunc){
    let options = this.httpOptionsService.getOptions("");
    this.http.post<any>(`http://localhost:8080/api/announcements/postAnnouncement`, payload, options).subscribe(returnFunc);
  }


}
