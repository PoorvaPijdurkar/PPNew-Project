import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'app/core/config/application-config.service';

@Injectable({
  providedIn: 'root',
})
export class SupplierDetailsService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('/api');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}
  fetchAllRecords(): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/supplier-details/fetchAll`);
  }
  fetchAllByCriteria(): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/supplier-details/fetchAllByCriteria?status.equals=APPROVED`);
  }
  create(supplier: any): Observable<any> {
    return this.http.post<any>(`${this.resourceUrl}/supplier-details`, supplier);
  }
  update(supplier: any): Observable<any> {
    return this.http.put<any>(`${this.resourceUrl}/supplier-details`, supplier);
  }
  delete(supplierId: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/supplier-details/delete/${supplierId}`);
  }
  fetchAllOrdersAssignedByUserId(userId: any): Observable<any> {
    return this.http.get<any>(` /api/ordersAssignment/fetchAllByCriteria?userId.equals=8'`);
  }
}
