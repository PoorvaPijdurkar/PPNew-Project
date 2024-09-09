import { HttpClient } from '@angular/common/http';
import { ElementRef, Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import * as Highcharts from 'highcharts';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/ordersDetailsDashboard');
  private resourceUrl1 = this.applicationConfigService.getEndpointFor('api');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  fetchOrdersDetailsInRange(formattedStartDate: string, formattedEndDate: string): Observable<any> {
    const url = `${this.resourceUrl}/getRangeDateOrders?startDate=${formattedStartDate}&endDate=${formattedEndDate}`;
    return this.http.get<any>(url).pipe(
      catchError(error => {
        console.error('Error fetching data:', error);
        return throwError(error);
      })
    );
  }

  createChart(el: ElementRef, cfg: Highcharts.Options): void {
    Highcharts.chart(el.nativeElement, cfg);
  }

  fetchPercentageForRange(formattedStartDate: string, formattedEndDate: string): Observable<any> {
    const url = `${this.resourceUrl}/getPercentageForRange?startDate=${formattedStartDate}&endDate=${formattedEndDate}`;
    return this.http.get<any>(url);
  }

  fetchOrdersDetailsInBetween(formattedStartDate: string, formattedEndDate: string): Observable<any> {
    const url = `${this.resourceUrl}/getOrdersDetailsInBetween?startDate=${formattedStartDate}&endDate=${formattedEndDate}`;
    return this.http.get<any>(url);
  }

  fetchOrdersSummaryDetailsInRange(
    formattedStartDate: string,
    formattedEndDate: string,
    status: string,
    page: number,
    size: number
  ): Observable<any> {
    let url = `${this.resourceUrl1}/getOrdersSummaryDetailsInRange?effectiveDate.greaterThanOrEqual=${formattedStartDate}&effectiveDate.lessThanOrEqual=${formattedEndDate}&page=${page}&size=${size}`;

    if (![null, undefined, 'ALL'].includes(status)) {
      url = url + `&orderStatus.equals=${status}`;
    }
    return this.http.get<any>(url);
  }

  fetchOrderAssignmentDetails(orderNumber: string): Observable<any> {
    const url = `${this.resourceUrl1}/getOrderAssignmentDetails?orderNumber=${orderNumber}`;
    return this.http.get<any>(url);
  }

  private formatDate(date: Date): string {
    const day = this.padNumber(date.getDate());
    const month = this.padNumber(date.getMonth() + 1);
    const year = date.getFullYear();

    return `${day}/${month}/${year}`;
  }
  private padNumber(num: number): string {
    return num < 10 ? `0${num}` : `${num}`;
  }
}
