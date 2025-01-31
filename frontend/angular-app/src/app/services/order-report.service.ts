import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OrderReportDTO } from '../models/order-report-dto';

@Injectable({
  providedIn: 'root'
})
export class OrderReportService {
  private baseUrl = 'http://localhost:8080/api/orders/reports'; // ✅ Backend API

  constructor(private http: HttpClient) {}

  getOrderReports(): Observable<OrderReportDTO[]> {
    return this.http.get<OrderReportDTO[]>(this.baseUrl);
  }
  }

