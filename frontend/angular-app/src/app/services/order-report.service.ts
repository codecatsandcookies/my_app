import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OrderReportDTO } from '../models/order-report-dto';

@Injectable({
  providedIn: 'root'
})
export class OrderReportService {
  private baseUrl = 'https://ecommerce-spring-boot-production-95e6.up.railway.app:8080/api/orders/reports'; 

  constructor(private http: HttpClient) {}

  getOrderReports(): Observable<OrderReportDTO[]> {
    return this.http.get<OrderReportDTO[]>(this.baseUrl);
  }
  }

