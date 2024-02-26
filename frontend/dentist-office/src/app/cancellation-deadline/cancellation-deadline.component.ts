import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import { AppointmentService } from '../services/appointment.service';
import { CodeDTO } from '../dto/codeDTO';

@Component({
  selector: 'app-cancellation-deadline',
  templateUrl: './cancellation-deadline.component.html',
  styleUrls: ['./cancellation-deadline.component.css']
})
export class CancellationDeadlineComponent implements OnInit {

  constructor(private service: AppointmentService, @Inject(MAT_DIALOG_DATA) public data: any, public matDialogRef: MatDialogRef<CancellationDeadlineComponent>) { }

  periodForm!: FormGroup;
  newPeriod!: number;
  p: number = 24;

  async ngOnInit(): Promise<void> {
    this.initForm();
    let result = await this.service.periodGet().toPromise();
    console.log(result);
    this.periodForm.setValue({
      period: result
    })
  }

  initForm() {
    this.periodForm = new FormGroup({
      period: new FormControl(this.p, [Validators.required]),
    });
  }

  ngOnDestroy(): void {
    if (!this.newPeriod) {
      this.matDialogRef.close();
    } else {
      let dto: CodeDTO = {
        code: this.newPeriod
      }
      this.service.period(dto).subscribe({
        next: (result: any) => {
          console.log(result)
        },
        error: (error: { error: { message: undefined; }; }) => {
          if (error?.error?.message != undefined) {
            alert(error?.error?.message);
          }        
        }
      })
      this.matDialogRef.close(this.newPeriod);
    }
  }

  submitForm() {
    this.newPeriod = this.periodForm.get('period')?.value;
    console.log(this.newPeriod);
    this.ngOnDestroy();
  }

}
