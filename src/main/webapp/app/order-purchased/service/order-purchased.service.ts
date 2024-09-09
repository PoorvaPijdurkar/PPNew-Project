import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class OrderPurchasedService {
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}
  fetchAllOrderPurchased(): Observable<any> {
    return this.http.get<any>(`api/supplierPurchaseOrderDetails/fetchAllRecords`);
  }

  updateOrderPurchase(orderPurchase: any): Observable<any> {
    return this.http.put<any>(`/api/supplierPurchaseOrderDetails`, orderPurchase);
  }

  deleteOrderPurchase(orderPurchaseId: number): Observable<{}> {
    return this.http.delete(`/api/supplierPurchaseOrderDetails/${orderPurchaseId}`);
  }

  saveAllOrderPurchase(orderPurchase: any[]): Observable<any> {
    return this.http.post<any>(`/api/supplierPurchaseOrderDetails`, orderPurchase);
  }
}
