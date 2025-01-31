import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { CheckoutService } from './services/checkout.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angular-ecommerce';

  constructor(private http: HttpClient, private checkoutService: CheckoutService) {}

  cancelOrder() {
    this.checkoutService.cancelLatestOrder().subscribe(response => {
      if (response) {
        alert('Your latest order has been canceled.');
      } else {
        alert('No orders available to cancel.');
      }
    });
  }
}

