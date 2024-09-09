import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'app/core/config/application-config.service';

@Injectable({ providedIn: 'root' })
export class SalesOrderService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('/api/salesOrder');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  fetchAllSalesOrderRecords(): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/fetchAllSalesOrderRecords`);
  }
  create(salesOrder: any): Observable<any> {
    return this.http.post<any>(`${this.resourceUrl}/save`, salesOrder);
  }

  update(salesOrder: any): Observable<any> {
    return this.http.put<any>(`${this.resourceUrl}/update`, salesOrder);
  }

  delete(salesOrderId: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/delete/${salesOrderId}`);
  }
}
