/* eslint-disable @typescript-eslint/no-unnecessary-condition */
/* eslint-disable @typescript-eslint/explicit-function-return-type */
/* eslint-disable @typescript-eslint/no-unsafe-return */
/* eslint-disable @typescript-eslint/member-ordering */
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LeadSourceService } from 'app/lead-source/service/lead-source.service';
import { Observable, of } from 'rxjs';
import { map, startWith, switchMap } from 'rxjs/operators';
import { CustomerService } from '../service/customer.service';

@Component({
  selector: 'jhi-customer-add',
  templateUrl: './customer-add.component.html',
  styleUrls: ['./customer-add.component.scss'],
})
export class CustomerAddComponent implements OnInit {
  filteredStates: Observable<any[]> | undefined;
  filteredCities: Observable<any[]> | undefined;
  selected = '';
  isLoading = false;
  screenMode = 'add';
  isSaving = false;
  rowData: any = {};
  countries: any[] = [];
  cityData: any[] = [];
  states: any[] = [];
  cities: any[] = [];
  leadData: any[] = [];
  editForm = new FormGroup({
    id: new FormControl(null),
    firstName: new FormControl(null, { validators: [Validators.required, Validators.maxLength(60)] }),
    lastName: new FormControl(null, { validators: [Validators.required, Validators.maxLength(60)] }),
    companyName: new FormControl(null, { validators: [Validators.required] }),
    address: new FormControl(null, { validators: [Validators.required, Validators.maxLength(100)] }),
    addressLine1: new FormControl(null),
    addressLine2: new FormControl(null),
    city: new FormControl(null, { validators: [Validators.required] }),
    pin: new FormControl(null, { validators: [Validators.required] }),
    state: new FormControl(null, { validators: [Validators.required] }),
    country: new FormControl(null, { validators: [Validators.required] }),
    email: new FormControl(null, { validators: [Validators.required, Validators.email] }),
    mobileNo: new FormControl(null, {
      validators: [Validators.required, Validators.pattern(/^\d{10}$/), Validators.pattern(/^[0-9]+$/)],
    }),
    whatsAppNo: new FormControl(null, {
      validators: [Validators.required, Validators.pattern(/^\d{10}$/), Validators.pattern(/^[0-9]+$/)],
    }),
    gstNo: new FormControl(null, { validators: [Validators.required] }),
    leadSource: new FormControl(null, { validators: [Validators.required] }),
  });

  constructor(private router: Router, private customerService: CustomerService, private leadSourceService: LeadSourceService) {
    const extraParam: any = this.router.getCurrentNavigation()?.extras;
    if (![undefined, null].includes(extraParam)) {
      const stateData = extraParam['state'];
      this.screenMode = stateData.mode || 'add';
      this.rowData = stateData.rowData || {};
    }
    if (['edit', 'view'].includes(this.screenMode)) {
      Object.keys(this.rowData).forEach(key => {
        if (this.editForm.get(key)) {
          this.editForm.get(key)?.setValue(this.rowData[key]);
        }
      });
    }
    if (['view'].includes(this.screenMode)) {
      this.editForm.disable();
    }
  }

  isAddMode = () => this.screenMode === 'add';
  isEditMode = () => this.screenMode === 'edit';
  isViewMode = () => this.screenMode === 'view';

  save(): void {
    if (this.editForm.valid) {
      this.isSaving = true;
      const customer: any = this.editForm.getRawValue();
      const observable: Observable<any> = customer.id ? this.customerService.update(customer) : this.customerService.create(customer);

      observable.subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    }
  }

  updateLeadSourceDetails(sourceType: any): void {
    const lead = this.leadData.find((leads: any) => leads.sourceType === sourceType);
    if (lead) {
      this.editForm.patchValue(lead);
    }
  }

  onLeadSourceChange(): void {
    const selectedLeadSource = this.editForm.get('leadSource')?.value;
    this.updateLeadSourceDetails(selectedLeadSource);
  }

  loadAllLeadSourceRecords(): void {
    this.leadSourceService.fetchLeadSourceTypeRecords().subscribe({
      next: (res: any[]) => {
        if (res.length > 0) {
          this.leadData = res;
        } else {
          console.log('No records found');
        }
      },
      error(error) {
        console.error('Error fetching lead source data:', error);
      },
    });
  }

  ngOnInit(): void {
    this.isLoading = true;
    this.loadAllLeadSourceRecords();

    this.filteredStates = this.editForm.get('state')?.valueChanges.pipe(
      startWith(''),
      switchMap(value => {
        const countryId = this.editForm.get('country')?.value;
        if (countryId) {
          return this.customerService.getStates(countryId);
        } else {
          return of([]);
        }
      })
    );

    this.editForm.get('state')?.valueChanges.subscribe(() => this.onStateChange());

    this.filteredCities = this.editForm.get('city')?.valueChanges.pipe(
      startWith(''),
      switchMap(value => {
        const stateCode = this.editForm.get('state')?.value;
        if (stateCode) {
          return this.customerService.getCities(stateCode);
        } else {
          return of([]);
        }
      })
    );
    this.loadCountries();
  }

  loadCountries(): void {
    this.customerService.fetchCountries().subscribe({
      next: (countries: any) => {
        this.countries = countries;
      },
      error(error: any) {
        console.error('Error fetching country data', error);
      },
    });
  }

  onCountryChange(): void {
    const countryId = this.editForm.get('country')?.value;
    if (countryId) {
      this.customerService.getStates(countryId).subscribe({
        next: (states: any[]) => {
          this.states = states;
          this.editForm.get('state')?.reset();
          this.editForm.get('city')?.reset();
        },
        error(error) {
          console.error('Error fetching states', error);
        },
      });
    }
  }

  onStateChange(): void {
    debugger;
    const stateCode = this.editForm.get('state')?.value;
    if (stateCode) {
      this.customerService.getCities(stateCode).subscribe({
        next: (cityData: any[]) => {
          this.cityData = cityData;
          this.filteredCities = of(cityData);
          this.editForm.get('city')?.reset();
          console.log('Cities loaded:', cityData);
        },
        error(error) {
          console.error('Error fetching cities', error);
        },
      });
    }
  }

  private _filterStates(value: string): any[] {
    const filterValue = value.toLowerCase();
    return this.states.filter(state => state.stateName.toLowerCase().includes(filterValue));
  }

  private _filterCities(value: string): any[] {
    const filterValue = value.toLowerCase();
    return this.cityData.filter(city => city.cityName.toLowerCase().includes(filterValue));
  }

  displayFn(state: any): string {
    return state?.stateName ? state.stateName : '';
  }

  displayCityFn(city: any): string {
    return city?.cityName ? city.cityName : '';
  }

  previousState(): void {
    this.router.navigate(['customer']);
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }
}
