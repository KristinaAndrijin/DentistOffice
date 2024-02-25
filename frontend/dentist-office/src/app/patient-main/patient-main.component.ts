import { Component, OnInit } from '@angular/core';
import { PatientService } from '../services/patient.service';
import { JwtService } from '../services/jwt.service';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CreateAppointmentComponent } from '../create-appointment/create-appointment.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-patient-main',
  templateUrl: './patient-main.component.html',
  styleUrls: ['./patient-main.component.css']
})
export class PatientMainComponent implements OnInit {

  constructor(private service: PatientService, private jwt: JwtService, private router:Router, private dialog: MatDialog) { }

  
  appointmentForm!: FormGroup;
  times = ["aa", "la", "ba"];

  ngOnInit(): void {
    // this.tryPatient();
  }

  tryPatient() {
    this.service.tryPatient().subscribe({
      next: (result: any) => {
        console.log(result)
      },
      error: (error: { error: { message: undefined; }; }) => {
        if (error?.error?.message != undefined) {
          alert(error?.error?.message);
        }        
      }
    })
  }

  testbtn(){
    let obj = {
      times: this.times
    }
    const dialogRef = this.dialog.open(CreateAppointmentComponent, {
      data: {obj: obj},
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
    });
    // this.service.test().subscribe({
    //   next: (result: any) => {
    //     console.log(result)
    //   },
    //   error: (error: { error: { message: undefined; }; }) => {
    //     if (error?.error?.message != undefined) {
    //       alert(error?.error?.message);
    //     }        
    //   }
    // })
  }

}
