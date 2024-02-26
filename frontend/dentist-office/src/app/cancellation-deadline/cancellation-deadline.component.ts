import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-cancellation-deadline',
  templateUrl: './cancellation-deadline.component.html',
  styleUrls: ['./cancellation-deadline.component.css']
})
export class CancellationDeadlineComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public matDialogRef: MatDialogRef<CancellationDeadlineComponent>) { }

  periodForm!: FormGroup;
  newPeriod!: number;

  ngOnInit(): void {
    console.log(this.data);
    this.initForm();
  }

  initForm() {
    this.periodForm = new FormGroup({
      period: new FormControl(this.data.obj, [Validators.required]),
    });
  }

  ngOnDestroy(): void {
    if (!this.newPeriod) {
      this.matDialogRef.close();
    } else {
      this.matDialogRef.close(this.newPeriod);
    }
  }

  submitForm() {
    this.newPeriod = this.periodForm.get('period')?.value;
    this.ngOnDestroy();
  }

}