import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtService } from '../services/jwt.service';
import { MatDialog } from '@angular/material/dialog';
import { CancellationDeadlineComponent } from '../cancellation-deadline/cancellation-deadline.component';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private router: Router, private jwtService: JwtService, private dialog: MatDialog) { }

  isDentist: boolean = true;

  ngOnInit(): void {
    if (this.jwtService.getRole()?.includes("DENTIST")) {
      this.isDentist = true;
    } else {
      this.isDentist = false;
    }
  }

  goToMainPage() {
    this.router.navigate(['main']);
  }

  logout() {
    this.jwtService.logout();
    this.router.navigate(['']).then(()=>{location.reload();});
  }

  configCancelTime() {
    let obj = 5;
    const dialogRef = this.dialog.open(CancellationDeadlineComponent, {
      data: {obj: obj},
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
    });
  }

}
