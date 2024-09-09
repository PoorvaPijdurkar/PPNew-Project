import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { SupplierDetailsService } from '../service/supplier-details.service';
import { OrderService } from '../../order/service/order.service';
import { forkJoin } from 'rxjs';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-supplier-details-add-update',
  templateUrl: './supplier-details-add-update.component.html',
  styleUrls: ['./supplier-details-add-update.component.scss'],
})
export class SupplierDetailsAddUpdateComponent implements OnInit {
  selected = '';
  orderNumberOptions: string[] = [];
  isLoading = false;
  screenMode = 'add';
  isSaving = false;
  userData: any[] = [];
  orderList: any[] = [];
  orderData: any[] = [];
  rowData: any = {};
  supplierTypes = ['Trader', 'Farmer'];
  editForm = new FormGroup({
    id: new FormControl(null),
    orderNumber: new FormControl(null),
    supplierType: new FormControl(null, { validators: [Validators.required] }),
    firstName: new FormControl(null, { validators: [Validators.required, Validators.maxLength(60)] }),
    lastName: new FormControl(null, { validators: [Validators.required, Validators.maxLength(60)] }),
    emailId: new FormControl(null, { validators: [Validators.required, Validators.email] }),
    address: new FormControl(null, { validators: [Validators.required, Validators.maxLength(100)] }),
    mobileNumber: new FormControl(null, {
      validators: [Validators.required, Validators.pattern(/^\d{10}$/), Validators.pattern(/^[0-9]+$/)],
    }),
    gstNumber: new FormControl(null, { validators: [Validators.pattern(/^[0-9]+$/)] }),
    totalLandHolding: new FormControl(null, { validators: [Validators.min(0), Validators.pattern(/^\d+(\.\d{1,2})?$/)] }),
  });
  constructor(private router: Router, private supplierDetailsService: SupplierDetailsService, private orderService: OrderService) {
    const extraParam: any = this.router.getCurrentNavigation()?.extras;
    if (![undefined, null].includes(extraParam)) {
      const stateData = extraParam['state'];
      this.screenMode = stateData.mode || 'add';
      this.rowData = stateData.rowData || {};
      this.userData = stateData.userData || [];
    }
    if (['edit', 'view'].includes(this.screenMode)) {
      this.editForm.get('id')?.setValue(this.rowData['id']);
      this.editForm.get('supplierType')?.setValue(this.rowData['supplierType']);
      this.editForm.get('firstName')?.setValue(this.rowData['firstName']);
      this.editForm.get('lastName')?.setValue(this.rowData['lastName']);
      this.editForm.get('emailId')?.setValue(this.rowData['emailId']);
      this.editForm.get('address')?.setValue(this.rowData['address']);
      this.editForm.get('mobileNumber')?.setValue(this.rowData['mobileNumber']);
      this.editForm.get('gstNumber')?.setValue(this.rowData['gstNumber']);
      this.editForm.get('totalLandHolding')?.setValue(this.rowData['totalLandHolding']);
    }
    if (['view'].includes(this.screenMode)) {
      this.editForm.get('id')?.disable();
      this.editForm.get('supplierType')?.disable();
      this.editForm.get('firstName')?.disable();
      this.editForm.get('lastName')?.disable();
      this.editForm.get('emailId')?.disable();
      this.editForm.get('address')?.disable();
      this.editForm.get('mobileNumber')?.disable();
      this.editForm.get('gstNumber')?.disable();
      this.editForm.get('totalLandHolding')?.disable();
    }
  }

  isAddMode(): boolean {
    return this.screenMode === 'add';
  }

  isEditMode(): boolean {
    return this.screenMode === 'edit';
  }
  isViewMode(): boolean {
    return this.screenMode === 'view';
  }

  save(): void {
    if (this.editForm.valid) {
      this.isSaving = true;

      const supplier: any = this.editForm.getRawValue();
      if (supplier.id !== null) {
        this.supplierDetailsService.update(supplier).subscribe({
          next: () => this.onSaveSuccess(),
          error: () => this.onSaveError(),
        });
      } else {
        this.supplierDetailsService.create(supplier).subscribe({
          next: () => this.onSaveSuccess(),
          error: () => this.onSaveError(),
        });
      }
    }
  }

  previousState() {
    this.router.navigate(['supplier']);
  }

  ngOnInit(): void {
    //     this.loadAll();
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }
}
