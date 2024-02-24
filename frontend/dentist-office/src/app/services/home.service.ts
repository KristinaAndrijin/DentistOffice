import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CodeDTO } from '../dto/codeDTO';
import { StringDTO, StringCodeDTO } from '../dto/stringDTO';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  constructor(private http: HttpClient) { }

  test():Observable<any>{
    return this.http.get(`${environment.apiUrl}test/hi`);
  }

  verifyCode(code: CodeDTO):Observable<any>{
    return this.http.post(`${environment.apiUrl}user/verifyDentistCode`, code);
  }

  handleSignIn(dto: StringCodeDTO):Observable<StringDTO>{
    return this.http.post<StringDTO>(`${environment.apiUrl}user/handleSignIn`, dto);
  }
}
