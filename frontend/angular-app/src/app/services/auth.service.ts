import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, Observable, tap, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loginUrl = 'http://localhost:8080/api/auth/login';
  private registerUrl = 'http://localhost:8080/api/auth/register';

  constructor(private httpClient: HttpClient, private router: Router) {}

  login(email: string, password: string): Observable<any> {
    return this.httpClient.post<any>(this.loginUrl, { email, password }).pipe(
      tap(response => {
        if (response.token) {
          console.log("Token received:", response.token); // Debugging
          localStorage.setItem('authToken', response.token);
        } else {
          console.error("No token received in response.");
        }
      }),
      catchError(error => {
        console.error("Login failed:", error);
        return throwError(() => new Error("Login failed."));
      })
    );
  }

  register(email: string, password: string) {
    return this.httpClient.post<{ message: string }>('http://localhost:8080/api/auth/register', {
      email,
      password
    }, { observe: 'response' }); // 👈 This will include full response details
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('authToken');
  }

  logout(): void {
    localStorage.removeItem('authToken');
    this.router.navigate(['/login']); // Redirect to login page
  }
}
