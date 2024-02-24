import { Component, OnInit } from '@angular/core';
import { PatientService } from '../services/patient.service';
import { JwtService } from '../services/jwt.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-patient-main',
  templateUrl: './patient-main.component.html',
  styleUrls: ['./patient-main.component.css']
})
export class PatientMainComponent implements OnInit {

  constructor(private service: PatientService, private jwt: JwtService, private router:Router) { }

  ngOnInit(): void {
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
    // this.router.navigate(["access-denied"]);
  }

  testbtn(){
    this.service.test().subscribe({
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

}
