import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'app/core/config/application-config.service';

@Injectable({ providedIn: 'root' })
export class BOMService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/bom');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  fetchAllBomRecords(): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/fetchAll`);
  }
  createBom(bom: any): Observable<any> {
    return this.http.post<any>(`${this.resourceUrl}/save`, bom);
  }
  update(bom: any): Observable<any> {
    return this.http.put<any>(`${this.resourceUrl}/update`, bom);
  }
  deleteBom(bomId: number): Observable<any> {
    return this.http.delete(`${this.resourceUrl}/delete?id=${bomId}`);
  }
  fetchAllSkuByCriteria(productType: string): Observable<any> {
    return this.http.get<any>(`/api/sku/fetchAllByCriteria?productType.notEquals=${productType}`);
  }

  fetchAllBomProductData(): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/fetchAllRecords`);
  }

  fetchAllProductDataByBomId(bomId: string): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/getBomsDetails?bomId=${bomId}`);
  }
}
