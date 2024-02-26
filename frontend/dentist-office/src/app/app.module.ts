import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatCommonModule } from '@angular/material/core';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatDialogModule} from "@angular/material/dialog";
import {MatTableModule} from "@angular/material/table";
import {MatPaginatorModule} from "@angular/material/paginator";


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { JwtInterceptorService } from './services/jwt-interceptor.service';
import { ErrorInterceptor } from './error-interceptor';
import { DenyAccessComponent } from './deny-access/deny-access.component';
import { MainComponent } from './main/main.component';
import { NavbarComponent } from './navbar/navbar.component';
import { CreateAppointmentComponent } from './main/create-appointment/create-appointment.component';
import { CancelDialogComponent } from './main/cancel-dialog/cancel-dialog.component';
import { CancellationDeadlineComponent } from './cancellation-deadline/cancellation-deadline.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    DenyAccessComponent,
    CreateAppointmentComponent,
    MainComponent,
    NavbarComponent,
    CancelDialogComponent,
    CancellationDeadlineComponent
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
    MatDialogModule,
    MatTableModule,
    MatPaginatorModule,
    
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
