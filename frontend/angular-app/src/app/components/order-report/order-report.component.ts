
import { Component, OnInit } from '@angular/core';
import { OrderReportDTO } from '../../models/order-report-dto';
import { OrderReportService } from '../../services/order-report.service';



@Component({
  selector: 'app-order-report',
  templateUrl: './order-report.component.html',
  styleUrls: ['./order-report.component.css'],
})
export class OrderReportComponent implements OnInit {
  orderReports: OrderReportDTO[] = [];

  constructor(private orderService: OrderReportService) {}

  ngOnInit(): void {
    this.fetchReports();
  }

  fetchReports(): void {
    this.orderService.getOrderReports().subscribe((data) => {
      this.orderReports = data;
    });
  }
}
