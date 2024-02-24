import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor(private http: HttpClient) { }

  test():Observable<any>{
    return this.http.get(`${environment.apiUrl}test/jwt`);
  }

  tryPatient():Observable<any>{
    return this.http.get(`${environment.apiUrl}test/tryPatient`);
  }

  tryDentist():Observable<any>{
    return this.http.get(`${environment.apiUrl}test/tryDentist`);
  }

  tryBoth():Observable<any>{
    return this.http.get(`${environment.apiUrl}test/tryBoth`);
  }

}
