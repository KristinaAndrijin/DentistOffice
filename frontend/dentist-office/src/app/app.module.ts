import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatCommonModule } from '@angular/material/core';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { PatientMainComponent } from './patient-main/patient-main.component';
import { JwtInterceptorService } from './services/jwt-interceptor.service';
import { ErrorInterceptor } from './error-interceptor';
import { DenyAccessComponent } from './deny-access/deny-access.component';
import { DentistMainComponent } from './dentist-main/dentist-main.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    PatientMainComponent,
    DenyAccessComponent,
    DentistMainComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatCommonModule,
    MatSnackBarModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptorService,
      multi: true
    },
    { 
      provide: HTTP_INTERCEPTORS, 
      useClass: ErrorInterceptor, 
      multi: true 
    },
      

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
