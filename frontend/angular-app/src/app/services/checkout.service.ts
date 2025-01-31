import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of, tap } from 'rxjs';
import { Purchase } from '../common/purchase';
import { PurchaseResponse } from '../common/purchase-response';

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {

  private baseUrl = 'http://localhost:8080/api/products'
  private purchaseUrl = 'http://localhost:8080/api/checkout/purchase';
  private cancelOrderUrl = 'http://localhost:8080/api/checkout/cancelLatestOrder';

  constructor(private httpClient: HttpClient) { }

  placeOrder(purchase: Purchase): Observable<PurchaseResponse> {
    return this.httpClient.post<PurchaseResponse>(this.purchaseUrl, purchase).pipe(
      tap(response => {
        // Update stock after successful purchase
        purchase.orderItems.forEach(item => {
          if (item.productId) {
            this.updateProductStock(item.productId, item.quantity);
          }
        });
      })
    );
  }

  updateProductStock(productId: number, quantityPurchased: number): void {
    const updateUrl = `${this.baseUrl}/updateStock/${productId}`;
    
    this.httpClient.put(updateUrl, { quantity: quantityPurchased }).subscribe({
      next: () => console.log(`Stock updated for product ID: ${productId}`),
      error: err => console.error(`Failed to update stock: ${err}`)
    });
  }

  cancelLatestOrder(): Observable<string> {
    return this.httpClient.delete<string>('http://localhost:8080/api/checkout/cancelLatestOrder', { responseType: 'text' as 'json' });
  }
}