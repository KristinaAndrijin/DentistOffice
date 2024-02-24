import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { PatientMainComponent } from './patient-main/patient-main.component';
import { DentistMainComponent } from './dentist-main/dentist-main.component';
import { DenyAccessComponent } from './deny-access/deny-access.component';

const routes: Routes = [
  {path: "home", component: HomeComponent},
  {path: "", component: HomeComponent},
  {path: "patient", component: PatientMainComponent},
  {path: "dentist", component: DentistMainComponent},
  {path: "access-denied", component: DenyAccessComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
