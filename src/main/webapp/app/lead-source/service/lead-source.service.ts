import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'app/core/config/application-config.service';

@Injectable({ providedIn: 'root' })
export class LeadSourceService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('/api/leadSourceType');
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  fetchLeadSourceTypeRecords(): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/fetchLeadSourceTypeRecords`);
  }
  create(lead: any): Observable<any> {
    return this.http.post<any>(`${this.resourceUrl}/save`, lead);
  }

  update(lead: any): Observable<any> {
    return this.http.put<any>(`${this.resourceUrl}/update`, lead);
  }

  delete(leadSourceId: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/delete/${leadSourceId}`);
  }
}
