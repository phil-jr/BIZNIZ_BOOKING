import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SignInBusinessComponent } from './sign-in-business.component';

describe('SignInBusinessComponent', () => {
  let component: SignInBusinessComponent;
  let fixture: ComponentFixture<SignInBusinessComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SignInBusinessComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SignInBusinessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
