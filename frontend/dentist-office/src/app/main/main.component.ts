import { Component, OnInit, ViewChild } from '@angular/core';
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {ActivatedRoute, Router} from "@angular/router";
import {MatTableDataSource} from "@angular/material/table";
import { MatSort } from '@angular/material/sort';
import { JwtService } from '../services/jwt.service';
import { AppointmentDTO } from '../dto/AppointmentDTO';
import { AppointmentService } from '../services/appointment.service';
import { MatDialog } from '@angular/material/dialog';
import { CreateAppointmentComponent } from './create-appointment/create-appointment.component';
import { CancelDialogComponent } from './cancel-dialog/cancel-dialog.component';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  isTable: boolean = true;
  displayedColumns: string[] = ['startDate', 'startTime','duration', 'patient', 'cancel'];
  dataSource!: MatTableDataSource<AppointmentDTO>;
  totalElements: number = 0;
  appointments: AppointmentDTO[] = [];
  isDentist: boolean = true;
  times = ["aa", "la", "ba"];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  constructor(private router: Router, private route: ActivatedRoute, private jwtService: JwtService, private service: AppointmentService, private dialog: MatDialog) {
    this.appointments = [
      {
        startDate: "lalalal",
        startTime: "vreme",
        patient: "pacijent",
        duration: 4,
      },
      {
        startDate: "lalalal1",
        startTime: "vreme",
        patient: "pacijent",
        duration: 5,
      },
      {
        startDate: "lalalal4",
        startTime: "vreme",
        patient: "pacijent",
        duration: 41,
      },
      {
        startDate: "lalalal3",
        startTime: "vreme",
        patient: "pacijent",
        duration: 498,
      },
      {
        startDate: "lalalal5",
        startTime: "vreme",
        patient: "pacijent",
        duration: 498,
      },
      {
        startDate: "lalalal6",
        startTime: "vreme",
        patient: "pacijent@gmail.com",
        duration: 498,
      },
    ]
   }

  ngOnInit(): void {
    this.tryJwt();
    if (this.jwtService.getRole()?.includes("PATIENT")) {
      this.isDentist = false;
      this.displayedColumns = ['startDate', 'startTime', 'duration', 'cancel'];
    } else if (this.jwtService.getRole()?.includes("DENTIST")) {
      this.isDentist = true;
      this.displayedColumns = ['startDate', 'startTime', 'duration', 'patient', 'cancel'];
    }
    this.totalElements = 6;
    this.dataSource = new MatTableDataSource<AppointmentDTO>(this.appointments);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  cancel(obj: AppointmentDTO) {
    const dialogRef = this.dialog.open(CancelDialogComponent, {
      data: {obj: obj},
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
    });
  }

  table() {
    this.isTable = true;
  }

  card() {
    this.isTable = false;
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
      console.log(result);
    });
  }

}
