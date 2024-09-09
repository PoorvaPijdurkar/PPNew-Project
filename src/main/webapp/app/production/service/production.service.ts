import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';

@Injectable({ providedIn: 'root' })
export class ProductionService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/production');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}
  fetchAllRecords(): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/fetchAllRecords`);
  }
  create(production: any): Observable<any> {
    return this.http.post<any>(`${this.resourceUrl}/save`, production);
  }

  update(production: any): Observable<any> {
    return this.http.put<any>(`${this.resourceUrl}/update`, production);
  }

  delete(productionId: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/delete/${productionId}`);
  }
  fetchGenerateProductionData(bomId: number, batchSize: number): Observable<any> {
    return this.http.get(`${this.resourceUrl}/generateProductionData/${bomId}?batchSize=${batchSize}`);
  }

  generateProductionReport(batchNumber: string): Observable<{}> {
    return this.http.post(`${this.resourceUrl}/generateProductionReport/${batchNumber}`, null, { responseType: 'blob' });
  }
  fetchProductionDataByItemCode(batchNumber: string): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/getProductionDataByItemCode/${batchNumber}`);
  }
}
