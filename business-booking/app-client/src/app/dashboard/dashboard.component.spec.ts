import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardTestComponent } from './dashboard.component';

describe('DashboardTestComponent', () => {
  let component: DashboardTestComponent;
  let fixture: ComponentFixture<DashboardTestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardTestComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
