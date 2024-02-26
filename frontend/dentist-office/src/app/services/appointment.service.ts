import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AppointmentDTO, AppointmentIdDTO } from '../dto/AppointmentDTO';
import { CodeDTO } from '../dto/codeDTO';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  constructor(private http: HttpClient) { }

  addAppointment(appointment: AppointmentDTO):Observable<any>{
    return this.http.post(`${environment.apiUrl}appointment`, appointment);
  }

  getAppointments(method: string):Observable<AppointmentIdDTO[]>{
    if (method == "Weekly") {
      return this.http.get<AppointmentIdDTO[]>(`${environment.apiUrl}appointment/week`);
    } else {
      return this.http.get<AppointmentIdDTO[]>(`${environment.apiUrl}appointment/day`);
    }
  }

  deleteAppointment(id:number):Observable<any>{
    return this.http.delete(`${environment.apiUrl}appointment/${id}`);
  }

  period(dto: CodeDTO):Observable<any> {
      return this.http.post(`${environment.apiUrl}appointment/period`, dto);
  }

  periodGet():Observable<any> {
    return this.http.get(`${environment.apiUrl}appointment/period`);
}

}
