import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BusinessAnnouncementsComponent } from './business-announcements.component';

describe('BusinessAnnouncementsComponent', () => {
  let component: BusinessAnnouncementsComponent;
  let fixture: ComponentFixture<BusinessAnnouncementsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BusinessAnnouncementsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BusinessAnnouncementsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
