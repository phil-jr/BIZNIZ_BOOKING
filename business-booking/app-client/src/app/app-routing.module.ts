import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BusinessProfileComponent } from './business-profile/business-profile.component';
import { CustomerProfileComponent } from './customer-profile/customer-profile.component';
import { HomeComponent } from './home/home.component';
import { SignInPageComponent } from './sign-in-page/sign-in-page.component';
import { SignUpPageComponent } from './sign-up-page/sign-up-page.component';


const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'signin', component: SignInPageComponent },
  { path: 'signup', component: SignUpPageComponent },
  { path: 'customer/:customer', component: CustomerProfileComponent },
  { path: 'business/:business', component: BusinessProfileComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
