import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'app/core/config/application-config.service';

@Injectable({ providedIn: 'root' })
export class DepartmentService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/department');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}
  fetchAllRecords(): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/fetchAll`);
  }
  fetchApprovedDepartments(): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/fetchAllByCriteria?status.equals=APPROVED`);
  }
  create(department: any): Observable<any> {
    return this.http.post<any>(`${this.resourceUrl}/create`, department);
  }

  update(department: any): Observable<any> {
    return this.http.put<any>(`${this.resourceUrl}/department`, department);
  }

  delete(departmentId: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/Delete/${departmentId}`);
  }
}
