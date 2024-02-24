import { Component, OnInit } from '@angular/core';
import { HomeService } from '../services/home.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CodeDTO } from '../dto/codeDTO';
import { MatSnackBar } from '@angular/material/snack-bar';
import { StringDTO, StringCodeDTO } from '../dto/stringDTO';
import { JwtService } from '../services/jwt.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  codeForm!: FormGroup;
  emailForm!: FormGroup;
  inputEmail: boolean = true;
  postCodeForm: boolean = false;
  dentistSignIn: boolean = false;
  validEmail: boolean = true;

  constructor(private service: HomeService, private snackBar: MatSnackBar, private jwtService: JwtService, private router:Router) {}

  ngOnInit(): void {
    this.codeForm = new FormGroup({
      code: new FormControl('', [Validators.required])
    });
    this.emailForm = new FormGroup({
      email: new FormControl('', [Validators.required])
    });
  }

  testbtn(){
    this.service.test().subscribe({
      next: (result: any) => {
        console.log(result)
      },
      error: (error: { error: { message: undefined; }; }) => {
        if (error?.error?.message != undefined) {
          this.snackBar.open(error?.error?.message, undefined, {
            duration: 2000,
          });
        }        
      }
    })
  }

  submitCode() {
    const codeForm = this.codeForm.get('code')?.value;
    let dto: CodeDTO = {
      code: codeForm
    }
    // console.log(codeForm);
    this.service.verifyCode(dto).subscribe({
      next: (result: any) => {
        // console.log(result);
        this.postCodeForm = true;
        this.inputEmail = true;
        this.dentistSignIn = true;
      },
      error: (error: { error: { message: undefined; }; }) => {
        if (error?.error?.message != undefined) {
          this.snackBar.open(error?.error?.message, "OK", {
            duration: 5000,
          });
          this.codeForm.reset();
        }        
      }
    })
  }

  submitEmail() {
    const emailFromForm = this.emailForm.get('email')?.value;
    const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
    const isValidEmail = emailRegex.test(emailFromForm);
    if (isValidEmail) {
      this.validEmail = true;
      let dto: StringCodeDTO = {
        str: emailFromForm,
        code: this.dentistSignIn
      }
      // console.log(emailFromForm);
      this.service.handleSignIn(dto).subscribe({
        next: (result: any) => {
          // console.log(result.str);
          this.dentistSignIn = false;
          this.jwtService.setAccessToken(result.str);
          // console.log(this.jwtService.getRole());
          let role = this.jwtService.getRole();
          if (role == "ROLE_PATIENT") {
            this.router.navigate(["patient"]);
          } else if (role == "ROLE_DENTIST") {
            // this.router.navigate(["dentist"]);
            console.log("dentist :-)")
          } else {
            this.snackBar.open("who!?", undefined, {
              duration: 5000,
            });
          }
        },
        error: (error: { error: { message: undefined; }; }) => {
          if (error?.error?.message != undefined) {
            this.snackBar.open(error?.error?.message, undefined, {
              duration: 5000,
            });
            this.emailForm.reset();
          }        
        }
      })
    } else {
      this.validEmail = false;
    }
  }

  goToCode() {
    this.inputEmail = false;
  }

  goToEmail() {
    this.inputEmail = true;
  }

}
