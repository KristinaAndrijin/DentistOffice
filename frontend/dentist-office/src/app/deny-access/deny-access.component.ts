import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { JwtService } from '../services/jwt.service';

@Component({
  selector: 'app-deny-access',
  templateUrl: './deny-access.component.html',
  styleUrls: ['./deny-access.component.css']
})
export class DenyAccessComponent implements OnInit {

  constructor(private router: Router, private route: ActivatedRoute, private jwtService: JwtService) { }

  unauthorized: boolean = true; // 401
  page = "/home";

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      let code = params['code'];
      if (code == 401) {
        this.unauthorized = true;
      } else {
        let role = this.jwtService.getRole();
        if (role == "ROLE_PATIENT") {
          this.page = "/patient";
        } else if (role == "ROLE_DENTIST") {
          this.page = "/dentist";
        }
        this.unauthorized = false;
      }
      console.log(code);
    })
  }

}
