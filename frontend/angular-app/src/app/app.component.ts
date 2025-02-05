import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { CheckoutService } from './services/checkout.service';
import { Router } from '@angular/router'; 
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angular-app';

  constructor(private http: HttpClient, private checkoutService: CheckoutService, private router: Router, public authService: AuthService) {}

  cancelOrder() {
    this.checkoutService.cancelLatestOrder().subscribe(response => {
      if (response) {
        alert('Your latest order has been canceled.');
      } else {
        alert('No orders available to cancel.');
      }
    });
  }

  navigateToReports(): void {
    this.router.navigate(['/order-reports']); // Navigate to order reports page
  }

  isLoggedIn(): boolean {
    return !localStorage.getItem('authToken'); // Check if token exists
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}

