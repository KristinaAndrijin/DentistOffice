import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import jwtDecode, {JwtPayload } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class JwtService {

  constructor(private http: HttpClient) { }

  getAccessToken() {
    return localStorage.getItem('accessToken');
  }

  setAccessToken(accessToken: string) {
    localStorage.setItem('accessToken',accessToken);
  }

  logout(){
    localStorage.clear();
  }

  getEmail() : string | undefined{
    const jwt = this.getAccessToken();
    if(jwt){
      const decoded = jwtDecode(jwt) as JwtPayload;
      return decoded.sub; 
    }
    return undefined;
  }

  getRole() : string | undefined{
    const jwt = this.getAccessToken();
    if(jwt){
      const decoded = jwtDecode(jwt) as {role: string};
      return decoded.role; 
    }
    return undefined;
  }

  getId() : string | undefined{
    const jwt = this.getAccessToken();
    if(jwt){
      const decoded = jwtDecode(jwt) as {id: string};
      return decoded.id; 
    }
    return undefined;
  }

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
