import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { IUser } from '../../admin/user-management/user-management.model';

@Injectable({ providedIn: 'root' })
export class OrderService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/order');

  private resourceUserUrl = this.applicationConfigService.getEndpointFor('/api/admin/users');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}
  fetchAllRecords(): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/fetchAllRecords`);
  }

  fetchApprovedOrders(): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/fetchAllByCriteria?status.equals=APPROVED`);
  }

  fetchAllOrderByOrderNumber(orderNumber: any): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/fetchAllByCriteria?orderNumber.equals=${orderNumber}`);
  }

  create(order: any): Observable<any> {
    return this.http.post<any>(this.resourceUrl, order);
  }

  update(order: any): Observable<any> {
    return this.http.put<any>(`${this.resourceUrl}/update`, order);
  }

  delete(orderId: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/delete/${orderId}`);
  }

  fetchAllUsers(): Observable<any> {
    let options: HttpParams = new HttpParams();
    options.set('authorities', 'ROLE_FLD_OFFICER');
    // options.set("sort","id,asc");
    // return this.http.get<IUser[]>(this.resourceUserUrl, { params: options, observe: 'response' });
    return this.http.get<any>(`api/admin/user/fetchAll?authorities=ROLE_FLD_OFFICER`);
  }
  createAssignedOrder(orderAssignment: any): Observable<any> {
    return this.http.post<any>(`/api/ordersAssignment/create`, orderAssignment);
  }

  saveAllOrder(orderAssignment: any[]): Observable<any> {
    return this.http.post<any>(`/api/ordersAssignment/createAll`, orderAssignment);
  }

  updateAssignedOrder(orderAssignment: any): Observable<any> {
    return this.http.put<any>(`/api/ordersAssignment/update`, orderAssignment);
  }

  assignedOrderDelete(orderId: number): Observable<{}> {
    return this.http.delete<any>(`/api/ordersAssignment/Delete/${orderId}`);
  }

  deleteAllRecords(listOfId: any[]): Observable<any> {
    const params = new HttpParams().set('listOfId', listOfId.join(','));
    return this.http.delete<any>('/api/ordersAssignment/deleteAll', { params });
  }

  fetchAllOrdersAssigned(orderNumber: any): Observable<any> {
    return this.http.get<any>(` /api/ordersAssignment/fetchAllByCriteria?orderNumber.equals=${orderNumber}`);
  }

  fetchAllOrdersAssignedByUserId(userId: any): Observable<any> {
    return this.http.get<any>(` /api/ordersAssignment/fetchAllByCriteria?userId.equals=${userId}`);
  }
}
