import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BusinessAppointmentFormComponent } from './business-appointment-form/business-appointment-form.component';
import { BusinessProfileComponent } from './business-profile/business-profile.component';
import { CustomerProfileComponent } from './customer-profile/customer-profile.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { CreateFormComponent } from './dashboard/manage-forms/create-form/create-form.component';
import { HomeComponent } from './home/home.component';
import { SignInPageComponent } from './sign-in-page/sign-in-page.component';
import { SignUpPageComponent } from './sign-up-page/sign-up-page.component';


const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'signin', component: SignInPageComponent },
  { path: 'signup', component: SignUpPageComponent },
  { path: 'customer/:customer', component: CustomerProfileComponent },
  { path: 'business/:business', component: BusinessProfileComponent },
  { path: 'business/:business/:form', component: BusinessAppointmentFormComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'dashboard/create-form', component: CreateFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
