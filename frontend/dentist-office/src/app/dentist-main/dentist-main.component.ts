import { Component, OnInit } from '@angular/core';
import { DentistService } from '../services/dentist.service';

@Component({
  selector: 'app-dentist-main',
  templateUrl: './dentist-main.component.html',
  styleUrls: ['./dentist-main.component.css']
})
export class DentistMainComponent implements OnInit {

  constructor(private service: DentistService) { }

  ngOnInit(): void {
    this.service.tryDentist().subscribe({
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
