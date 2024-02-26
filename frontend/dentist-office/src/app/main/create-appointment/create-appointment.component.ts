import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import { AppointmentTimeDTO } from 'src/app/dto/AppointmentDTO';
import { AppointmentService } from 'src/app/services/appointment.service';

@Component({
  selector: 'app-create-appointment',
  templateUrl: './create-appointment.component.html',
  styleUrls: ['./create-appointment.component.css']
})
export class CreateAppointmentComponent implements OnInit {

  constructor(private service: AppointmentService, @Inject(MAT_DIALOG_DATA) public data: any, public matDialogRef: MatDialogRef<CreateAppointmentComponent>) { }

  appointmentForm!: FormGroup;
  times!: [];
  shouldGetData: boolean=false; // ne uzimaj ako je dijalog samo zatvoren vec samo ako je pritisnuto dugme
  isDentist: boolean = false;
  validEmail: boolean = true;

  ngOnInit(): void {
    this.shouldGetData = false;
    console.log(this.data);
    this.times = this.data.obj.times;
    this.isDentist = this.data.obj.isDentist;
    // this.tryPatient();
    this.initForm();

    this.appointmentForm.valueChanges.subscribe((value) => {
      this.changedValue(value);
    })
  }

  initForm() {
    const today = new Date().toISOString().split('T')[0];
    if (this.isDentist) {
        this.appointmentForm = new FormGroup({
          date: new FormControl(today, [Validators.required]),
          duration: new FormControl('30', [Validators.required]),
          time: new FormControl("", [Validators.required]),
          email: new FormControl('', [Validators.required]),
      });
    } else {
      this.appointmentForm = new FormGroup({
        date: new FormControl(today, [Validators.required]),
        duration: new FormControl('30', [Validators.required]),
        time: new FormControl('', [Validators.required]),
    });
    }

    let dto: AppointmentTimeDTO = {
      date: today,
      lastHour: false
    }
    this.service.getTimes(dto).subscribe({
      next: (result: any) => {
        // console.log(result)
        this.times = result;
      },
      error: (error: { error: { message: undefined; }; }) => {
        if (error?.error?.message != undefined) {
          alert(error?.error?.message);
        }        
      }
    })
  }

  submitForm() {
    const dateForm = this.appointmentForm.get('date')?.value;
    // console.log(dateForm);
    const durationForm = this.appointmentForm.get('duration')?.value;
    // console.log(durationForm);
    this.shouldGetData = true;
    if (this.isDentist) {
      const emailFromForm = this.appointmentForm.get('email')?.value;
      const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
      const isValidEmail = emailRegex.test(emailFromForm);
      if (isValidEmail) {
        this.validEmail = true;
        let ret = {
          date: this.appointmentForm.get('date')?.value,
          duration: this.appointmentForm.get('duration')?.value,
          time: this.appointmentForm.get('time')?.value,
          email: this.appointmentForm.get('email')?.value,
        }
        this.matDialogRef.close(ret);
      } else{
        console.log('email false')
        this.validEmail = false;
      }
    } else {
      let ret = {
        date: this.appointmentForm.get('date')?.value,
        duration: this.appointmentForm.get('duration')?.value,
        time: this.appointmentForm.get('time')?.value,
      }
      this.matDialogRef.close(ret);
    }
  }

  changedValue(value: any) {
    // console.log('Form value changed:', value);
    // console.log(value.date)
    // console.log(value.duration)
    // console.log(value.time)
    let dto: AppointmentTimeDTO = {
      date: value.date,
      lastHour: value.duration == 60
    }
    this.service.getTimes(dto).subscribe({
      next: (result: any) => {
        // console.log(result)
        this.times = result;
      },
      error: (error: { error: { message: undefined; }; }) => {
        if (error?.error?.message != undefined) {
          alert(error?.error?.message);
        }        
      }
    })
  }

  ngOnDestroy(): void {
    console.log(this.shouldGetData);
    if (this.shouldGetData) {
      if (this.isDentist) {
        const emailFromForm = this.appointmentForm.get('email')?.value;
        const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
        const isValidEmail = emailRegex.test(emailFromForm);
        if (isValidEmail) {
          this.validEmail = true;
          let ret = {
            date: this.appointmentForm.get('date')?.value,
            duration: this.appointmentForm.get('duration')?.value,
            time: this.appointmentForm.get('time')?.value,
            email: this.appointmentForm.get('email')?.value,
          }
          this.matDialogRef.close(ret);
        } else{
          console.log('email false')
          this.validEmail = false;
        }
      } else {
        let ret = {
          date: this.appointmentForm.get('date')?.value,
          duration: this.appointmentForm.get('duration')?.value,
          time: this.appointmentForm.get('time')?.value,
        }
        this.matDialogRef.close(ret);
      }
      // this.matDialogRef.close(ret);
      this.shouldGetData = false;
    } else {
      let some = {
        aaa: '222'
      }
      this.matDialogRef.close();
    }
    this.shouldGetData = false;
  }

}
