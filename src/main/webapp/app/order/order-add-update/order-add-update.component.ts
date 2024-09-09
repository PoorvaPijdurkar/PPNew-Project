import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SkuService } from '../../sku/service/sku.service';
import { HttpResponse } from '@angular/common/http';
import { OrderService } from '../service/order.service';
import _moment from 'moment';
import { MomentDateAdapter, MAT_MOMENT_DATE_ADAPTER_OPTIONS } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';

export const MY_FORMATS = {
  parse: {
    dateInput: 'L',
  },
  display: {
    dateInput: 'L',
  },
};

@Component({
  selector: 'jhi-order-add-update',
  templateUrl: './order-add-update.component.html',

  providers: [
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS],
    },
    { provide: MAT_DATE_FORMATS, useValue: MY_FORMATS },
  ],
})
export class OrderAddUpdateComponent implements OnInit {
  selected: any;
  screenMode = 'add';
  isSaving = false;
  rowData: any = {};
  skuData: any;
  currentDate!: Date;

  editForm = new FormGroup({
    orderNumber: new FormControl(null),
    orderDate: new FormControl(null),
    status: new FormControl(null, { validators: [Validators.required, Validators.maxLength(25)] }),
    product: new FormControl(null, { validators: [Validators.required] }),
    estimatedDate: new FormControl(null, { validators: [Validators.required] }),
    quantityRequired: new FormControl(null, {
      validators: [Validators.required, Validators.maxLength(10), Validators.pattern(/^\d+(\.\d{1,2})?$/)],
    }),
    ratePrice: new FormControl(null, {
      validators: [Validators.required, Validators.maxLength(10), Validators.pattern(/^\d+(\.\d{1,2})?$/)],
    }),
  });

  minDate: Date;

  constructor(private router: Router, private skuService: SkuService, private orderService: OrderService) {
    this.minDate = new Date();

    const extraParam: any = this.router.getCurrentNavigation()?.extras;

    if (![undefined, null].includes(extraParam)) {
      const stateData = extraParam['state'];
      this.screenMode = stateData['mode'];
      this.rowData = stateData['rowData'];
      this.skuData = stateData['skuData'];
      console.log('sku data', this.skuData);
      console.log('rowData data', this.rowData);
    }
    if (['edit', 'view'].includes(this.screenMode)) {
      this.editForm.get('orderNumber')?.setValue(this.rowData['orderNumber']);
      this.editForm.get('orderDate')?.setValue(this.rowData['orderDate']);
      this.editForm.get('status')?.setValue(this.rowData['status']);
      this.editForm.get('quantityRequired')?.setValue(this.rowData['quantityRequired']);
      this.editForm.get('ratePrice')?.setValue(this.rowData['ratePrice']);
      this.editForm.get('product')?.setValue(this.rowData['product']);
      const date = _moment(this.rowData['estimatedDate'], 'YYYY-MM-DD');
      // @ts-ignore
      this.editForm.get('estimatedDate')?.setValue(date);
      this.editForm.get('orderNumber')?.disable();
      this.editForm.get('orderDate')?.disable();
      this.editForm.get('product')?.disable();
    }
    if (['view'].includes(this.screenMode)) {
      this.editForm.get('status')?.disable();
      this.editForm.get('quantityRequired')?.disable();
      this.editForm.get('ratePrice')?.disable();
      this.editForm.get('product')?.disable();
      this.editForm.get('estimatedDate')?.disable();
    }
  }

  Filter = (d: Date): boolean => {
    const day = d.getDay();
    return day !== 0 && day !== 6;
  };

  isAddMode() {
    return this.screenMode === 'add';
  }

  isEditMode() {
    return this.screenMode === 'edit';
  }

  isViewMode() {
    return this.screenMode === 'view';
  }

  save(): void {
    this.isSaving = true;
    const order: any = this.editForm.getRawValue();
    const orderData = {
      id: order.id,
      sku: order.product,
      orderNumber: order.orderNumber,
      ratePrice: order.ratePrice,
      quantityRequired: order.quantityRequired,
      status: order.status,
      estimatedDate: order.estimatedDate.format('yyyy-MM-DD'),
      orderDate: this.currentDate.toISOString().slice(0, 10),
    };
    if (this.isAddMode()) {
      orderData['status'] = 'WAITING FOR APPROVAL';
    } else if (this.isEditMode()) {
      orderData['id'] = this.rowData['id'];
    }

    if (order.orderNumber !== null) {
      this.orderService.update(orderData).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      this.orderService.create(orderData).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    }
  }

  previousState() {
    this.router.navigate(['order']);
  }

  ngOnInit(): void {
    if (!this.isAddMode()) {
      const skuId = this.rowData.sku.id;
      const sku = this.skuData.find((item: any) => item.id === skuId);
      this.editForm.get('product')?.setValue(sku);
      this.editForm.get('product')?.updateValueAndValidity();
    }
    this.currentDate = new Date();
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }
}
