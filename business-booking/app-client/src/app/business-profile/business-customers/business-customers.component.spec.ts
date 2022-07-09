import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BusinessCustomersComponent } from './business-customers.component';

describe('BusinessCustomersComponent', () => {
  let component: BusinessCustomersComponent;
  let fixture: ComponentFixture<BusinessCustomersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BusinessCustomersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BusinessCustomersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
