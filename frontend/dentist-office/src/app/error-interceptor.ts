import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private router: Router) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
        //   this.router.navigate(['/login']);
            console.log("error intercept 401!");
            this.router.navigate(['access-denied'], { queryParams: { code: 401 }});
        } else if (error.status === 403) {
            console.log("error intercept 403!");
            this.router.navigate(['access-denied'], { queryParams: { code: 403 }});
        }
        return throwError(error);
      })
    );
  }
}
