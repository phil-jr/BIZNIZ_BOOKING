import { Component, Input, OnInit } from '@angular/core';
import { AnnouncementService } from 'src/app/service/announcement.service';
@Component({
  selector: 'business-announcements',
  templateUrl: './business-announcements.component.html',
  styleUrls: ['./business-announcements.component.css']
})
export class BusinessAnnouncementsComponent implements OnInit {

  @Input() business;

  announcementValue = "";

  constructor(public announcementService: AnnouncementService) { }

  ngOnInit(): void {
  }

  postAnnouncement(){
    let payload = {
      announcement: this.announcementValue
    }
    
    this.announcementService.postBusinessAnnouncement(payload, data => {
      console.log(data)
    });
  }

}
