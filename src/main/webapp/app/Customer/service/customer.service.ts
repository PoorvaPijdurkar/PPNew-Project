import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'app/core/config/application-config.service';

@Injectable({
  providedIn: 'root',
})
export class CustomerService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('/api');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}
  fetchAllRecords(): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/customerDetails/fetchAllRecords`);
  }
  create(customer: any): Observable<any> {
    return this.http.post<any>(`${this.resourceUrl}/customerDetails/save`, customer);
  }
  update(customer: any): Observable<any> {
    return this.http.put<any>(`${this.resourceUrl}/customerDetails/update`, customer);
  }
  delete(customerId: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/customerDetails/delete/${customerId}`);
  }
  fetchAllCityRecords(): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/city/fetchAllCityRecords`);
  }
  
  fetchAllStateRecords(): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/state/fetchAllRecords`);
  }

  fetchCountries(): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/country/fetchAllRecords`);
  }

  getStates(countryId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.resourceUrl}/state/fetchStateRecordsByCountryCode/${countryId}`);
  }
  
  getCities(stateCode: number): Observable<any> {
    debugger;
    return this.http.get(`${this.resourceUrl}/city/fetchCitiesRecordsByStateCode/${stateCode}`);
  }
  
  
}
