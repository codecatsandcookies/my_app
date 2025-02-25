import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of, tap, throwError } from 'rxjs';
import { Purchase } from '../common/purchase';
import { PurchaseResponse } from '../common/purchase-response';

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {

  private baseUrl = 'http://ecommerce-spring-boot:8080/api/products'
  private purchaseUrl = 'http://ecommerce-spring-boot:8080/api/checkout/purchase';
  private cancelOrderUrl = 'http://ecommerce-spring-boot:8080/api/checkout/cancelLatestOrder';
  private reportsUrl = 'http://ecommerce-spring-boot:8080/api/orders/reports';

  constructor(private httpClient: HttpClient) { }

  placeOrder(purchase: Purchase): Observable<PurchaseResponse> {
    const authToken = localStorage.getItem('authToken');
    const headers = new HttpHeaders({
                      'Authorization': `Bearer ${authToken}`,
                      'Content-Type': 'application/json'
                      });
    return this.httpClient.post<PurchaseResponse>(this.purchaseUrl, purchase).pipe(
      tap(response => {
        if (response) {
          console.log("Order placed successfully:", response);
          purchase.orderItems.forEach(item => {
            if (item.productId) {
              this.updateProductStock(item.productId, item.quantity);
            }
          });
        } else {
          console.error("Invalid response from API.");
        }
      }),
      catchError((error) => {
        console.error("Checkout failed:", error);
        return throwError(() => new Error("Checkout failed. Please try again."));
      })
    );
  }

  updateProductStock(productId: number, quantityPurchased: number): void {
    const updateUrl = `${this.baseUrl}/updateStock/${productId}`;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    this.httpClient.put(updateUrl, { quantity: quantityPurchased }, { headers }).subscribe({
      next: () => console.log(`Stock updated for product ID: ${productId}`),
      error: err => console.error(`Failed to update stock: ${err}`)
    });
  }

  cancelLatestOrder(): Observable<string> {
    return this.httpClient.delete<string>('http://ecommerce-spring-boot:8080/api/checkout/cancelLatestOrder', { responseType: 'text' as 'json' });
  }

  getOrderReports(): Observable<any> {
    const authToken = localStorage.getItem('authToken');  // Retrieve token
    if (!authToken) {
      console.error("No authentication token found!");
      return throwError(() => new Error("User is not authenticated."));
    }
  
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${authToken}`,
      'Content-Type': 'application/json'
    });
  
    return this.httpClient.get(this.reportsUrl, { headers }).pipe(
      catchError(error => {
        console.error("Error fetching order reports:", error);
        return throwError(() => new Error("Failed to fetch order reports."));
      })
    );
  }
}