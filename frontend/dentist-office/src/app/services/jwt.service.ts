import { Injectable } from '@angular/core';
import jwtDecode, {JwtPayload } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class JwtService {

  constructor() { }

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


}
