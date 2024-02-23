import { Component, OnInit } from '@angular/core';
import { HomeService } from '../services/home.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private service: HomeService) {}

  ngOnInit(): void {}

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
