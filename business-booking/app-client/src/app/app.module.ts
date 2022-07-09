import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatListModule } from "@angular/material/list";
import { MatIconModule } from "@angular/material/icon";
import { MatButtonModule } from "@angular/material/button";
import { MatTabsModule } from '@angular/material/tabs';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatMomentDateModule } from '@angular/material-moment-adapter';
import { MatRadioModule } from "@angular/material/radio";
import { FlexLayoutModule } from "@angular/flex-layout";
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { SignInPageComponent } from './sign-in-page/sign-in-page.component';
import { HomeComponent } from './home/home.component';
import { SignInBusinessComponent } from './sign-in-page/sign-in-business/sign-in-business.component';
import { SignInCustomerComponent } from './sign-in-page/sign-in-customer/sign-in-customer.component';
import { CustomerProfileComponent } from './customer-profile/customer-profile.component';
import { BusinessProfileComponent } from './business-profile/business-profile.component';
import { BusinessOverviewComponent } from './business-profile/business-overview/business-overview.component';
import { BookAppointmentComponent } from './business-profile/book-appointment/book-appointment.component';
import { BusinessReviewsComponent } from './business-profile/business-reviews/business-reviews.component';
import { BusinessAnnouncementsComponent } from './business-profile/business-announcements/business-announcements.component';
import { BusinessCustomersComponent } from './business-profile/business-customers/business-customers.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { BusinessAppointmentFormComponent } from './business-appointment-form/business-appointment-form.component';
import { ManageFormsComponent } from './dashboard/manage-forms/manage-forms.component';
import { DashboardMessagesComponent } from './dashboard/dashboard-messages/dashboard-messages.component';
import { DashboardDataComponent } from './dashboard/dashboard-data/dashboard-data.component';
import { DashboardSchedulingComponent } from './dashboard/dashboard-scheduling/dashboard-scheduling.component';
import { CreateFormComponent } from './dashboard/manage-forms/create-form/create-form.component';


@NgModule({
  declarations: [
    AppComponent,
    SignInPageComponent,
    HomeComponent,
    SignInBusinessComponent,
    SignInCustomerComponent,
    CustomerProfileComponent,
    BusinessProfileComponent,
    BusinessOverviewComponent,
    BookAppointmentComponent,
    BusinessReviewsComponent,
    BusinessAnnouncementsComponent,
    BusinessCustomersComponent,
    DashboardComponent,
    BusinessAppointmentFormComponent,
    ManageFormsComponent,
    DashboardMessagesComponent,
    DashboardDataComponent,
    DashboardSchedulingComponent,
    CreateFormComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatIconModule,
    MatButtonModule,
    MatTabsModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    MatSelectModule,
    MatDatepickerModule,
    MatMomentDateModule,
    MatRadioModule,
    MatCardModule,
    FlexLayoutModule,
    NgxChartsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
