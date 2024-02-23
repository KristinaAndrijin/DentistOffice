import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

constructor(private http: HttpClient) { }


test():Observable<any>{
  return this.http.get(`${environment.apiUrl}test/hi`);
}

}
