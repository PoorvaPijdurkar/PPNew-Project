import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { IUser } from '../../admin/user-management/user-management.model';

@Injectable({ providedIn: 'root' })
export class OrderAssignmentService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/orders/order');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}
  createSupplier(orderAssignment: any): Observable<any> {
    return this.http.post<any>(`/api/supplier-details`, orderAssignment);
  }

  updateSupplier(orderAssignment: any): Observable<any> {
    return this.http.put<any>(`/api/supplier-details`, orderAssignment);
  }

  createSupplierPurchaseOrder(supplierPurchaseOrder: any): Observable<any> {
    return this.http.post<any>(`/api/supplierPurchaseOrderDetails`, supplierPurchaseOrder);
  }

  fetchAllSupplierDetails() {
    return this.http.get<any>(`/api/supplier-details/fetchAllRecords`);
  }
}
