import { Component, OnInit, ViewChild } from '@angular/core';
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {ActivatedRoute, Router} from "@angular/router";
import {MatTableDataSource} from "@angular/material/table";
import { MatSort } from '@angular/material/sort';
import { JwtService } from '../services/jwt.service';
import { AppointmentIdDTO, AppointmentDTO } from '../dto/AppointmentDTO';
import { AppointmentService } from '../services/appointment.service';
import { MatDialog } from '@angular/material/dialog';
import { CreateAppointmentComponent } from './create-appointment/create-appointment.component';
import { CancelDialogComponent } from './cancel-dialog/cancel-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Location } from '@angular/common';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  isTable: boolean = true;
  displayedColumns: string[] = ['startDate', 'startTime','duration', 'patient', 'cancel'];
  dataSource: MatTableDataSource<AppointmentIdDTO> = new MatTableDataSource<AppointmentIdDTO>();
  totalElements: number = 0;
  appointments: AppointmentIdDTO[] = [];
  isDentist: boolean = true;
  times = ["9:00", "9:30", "10:00"];
  options = ["Daily", "Weekly"];
  methodForm!: FormGroup;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  constructor(private router: Router, private route: ActivatedRoute, private jwtService: JwtService, private service: AppointmentService, 
              private dialog: MatDialog, private snackBar: MatSnackBar, private location: Location) {
   }

   async ngOnInit(): Promise<void> {
    this.methodForm = new FormGroup({
      method: new FormControl('Daily', [Validators.required]),
    });
    this.methodForm.valueChanges.subscribe((value) => {
      this.changedValue(value);
    })
    this.tryJwt();
    if (this.jwtService.getRole()?.includes("PATIENT")) {
      this.isDentist = false;
      this.displayedColumns = ['startDate', 'startTime', 'duration', 'cancel'];
    } else if (this.jwtService.getRole()?.includes("DENTIST")) {
      this.isDentist = true;
      this.displayedColumns = ['startDate', 'startTime', 'duration', 'patient', 'cancel'];
    }
    await this.getAll();
      
  }

  async getAll(): Promise<void> {
    try {
      let method = this.methodForm.get('method')?.value;
      console.log(method);
      let result = await this.service.getAppointments(method).toPromise();
      if (result != undefined) {
        this.appointments = result;
      } else {
        console.log(":-(")
        this.appointments = [];
      }
      this.totalElements = this.appointments.length;
      this.dataSource = new MatTableDataSource<AppointmentIdDTO>(this.appointments);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    } catch (error) {
      console.log(error)
    }
  }

  cancel(obj: AppointmentIdDTO) {
    console.log(obj)
    const dialogRef = this.dialog.open(CancelDialogComponent, {
      data: {obj: obj},
    });
    dialogRef.afterClosed().subscribe(shouldCancel => {
      console.log(shouldCancel);
      if (shouldCancel) {
        this.service.deleteAppointment(obj.id).subscribe({
          next: (result: any) => {
            console.log(result)
            this.getAll();
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
    });
  }

  table() {
    this.isTable = true;
    this.getAll();
  }

  card() {
    this.isTable = false;
    this.getAll();
  }

  tryJwt() {
    this.jwtService.tryBoth().subscribe({
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

  add() {
    let obj = {
      times: this.times,
      isDentist: this.isDentist
    }
    const dialogRef = this.dialog.open(CreateAppointmentComponent, {
      data: {obj: obj},
    });
    dialogRef.afterClosed().subscribe(result => {
      // console.log(result.email);
      let email = result.email != undefined ? result.email : 'None';
      let dto: AppointmentDTO = {
        startDate: result.date,
        startTime: result.time,
        patient: email,
        duration: parseInt(result.duration)
      }
      console.log(dto);
      this.service.addAppointment(dto).subscribe({
        next: (result: any) => {
          console.log(result)
          this.getAll();
        },
        error: (error: { error: { message: undefined; }; }) => {
          if (error?.error?.message != undefined) {
            this.snackBar.open(error?.error?.message, undefined, {
              duration: 2000,
            });
          }        
        }
      })
    });
  }

  changedValue(value: any) {
    console.log('Form value changed:', value.method);
    this.getAll();
    // console.log(value.date)
    // console.log(value.duration)
    // console.log(value.time)
  }

}
