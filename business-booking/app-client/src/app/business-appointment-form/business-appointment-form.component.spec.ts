import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BusinessAppointmentFormComponent } from './business-appointment-form.component';

describe('BusinessAppointmentFormComponent', () => {
  let component: BusinessAppointmentFormComponent;
  let fixture: ComponentFixture<BusinessAppointmentFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BusinessAppointmentFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BusinessAppointmentFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
