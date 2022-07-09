import { Component, Input, OnInit } from '@angular/core';
@Component({
  selector: 'business-announcements',
  templateUrl: './business-announcements.component.html',
  styleUrls: ['./business-announcements.component.css']
})
export class BusinessAnnouncementsComponent implements OnInit {

  @Input() business;

  constructor() { }

  ngOnInit(): void {
  }

}
