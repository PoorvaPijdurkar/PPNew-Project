import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';

@Injectable({ providedIn: 'root' })
export class SkuService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/sku');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}
  fetchAllRecords(): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/fetchAllRecords`);
  }
  create(sku: any): Observable<any> {
    return this.http.post<any>(this.resourceUrl, sku);
  }

  update(sku: any): Observable<any> {
    return this.http.put<any>(this.resourceUrl, sku);
  }

  delete(skuId: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${skuId}`);
  }
}
