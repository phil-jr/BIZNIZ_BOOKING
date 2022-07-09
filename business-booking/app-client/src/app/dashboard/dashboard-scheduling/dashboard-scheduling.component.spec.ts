import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardSchedulingComponent } from './dashboard-scheduling.component';

describe('DashboardSchedulingComponent', () => {
  let component: DashboardSchedulingComponent;
  let fixture: ComponentFixture<DashboardSchedulingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardSchedulingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardSchedulingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
