import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class AuthGuard {
  constructor(private router: Router) {}

  canMatch(): boolean {
    if (localStorage.getItem('authToken')) {
      return true; // User is authenticated
    } else {
      this.router.navigate(['/login']);
      return false; // Redirect to login page if not authenticated
    }
  }
}
