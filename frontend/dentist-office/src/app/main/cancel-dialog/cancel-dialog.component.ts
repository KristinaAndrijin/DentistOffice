import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-cancel-dialog',
  templateUrl: './cancel-dialog.component.html',
  styleUrls: ['./cancel-dialog.component.css']
})
export class CancelDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public matDialogRef: MatDialogRef<CancelDialogComponent>) { }

  ret: boolean = false;

  ngOnInit(): void {
    //console.log(this.data);
  }

  ngOnDestroy(): void {
    this.matDialogRef.close(this.ret);
  }

  cancel() {
    this.ret = true;
    this.ngOnDestroy();
  }

  no() {
    this.ret = false;
    this.ngOnDestroy();
  }

}
