import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { SalesOrderService } from '../service/sales-order.service';
import { CustomerService } from 'app/Customer/service/customer.service';
import { BOMService } from 'app/bom/service/bom.service';
import _moment from 'moment';
import { MomentDateAdapter, MAT_MOMENT_DATE_ADAPTER_OPTIONS } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { AccountService } from 'app/core/auth/account.service';

export const MY_FORMATS = {
  parse: {
    dateInput: 'L',
  },
  display: {
    dateInput: 'L',
  },
};

@Component({
  selector: 'jhi-sales-order-add',
  templateUrl: './sales-order-add.component.html',
  styleUrls: ['./sales-order-add.component.scss'],
  providers: [
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS],
    },
    { provide: MAT_DATE_FORMATS, useValue: MY_FORMATS },
  ],
})
export class SalesOrderAddComponent implements OnInit {
  selected: any;
  screenMode = 'add';
  isSaving = false;
  rowData: any = {
    createdBy: '',
  };
  CustomerData: any = [];
  ItemData: any = [];
  minDate: Date;
  loginUserName = ``;
  editForm = new FormGroup({
    id: new FormControl(null),
    customerName: new FormControl(null, { validators: [Validators.required] }),
    itemName: new FormControl(null, { validators: [Validators.required] }),
    deliveryDate: new FormControl(null, { validators: [Validators.required] }),
    orderQuantity: new FormControl(null, [Validators.required, Validators.min(0)]),
    unitOfMeasure: new FormControl({ value: '', disabled: false }),
  });

  constructor(
    private router: Router,
    private salesOrderService: SalesOrderService,
    private customerService: CustomerService,
    private bomService: BOMService,
    private accountService: AccountService
  ) {
    this.minDate = new Date();
    const extraParam: any = this.router.getCurrentNavigation()?.extras;
    if (extraParam) {
      const stateData = extraParam.state;
      this.screenMode = stateData.mode;
      this.rowData = stateData.rowData;
    }

    if (['edit', 'view'].includes(this.screenMode)) {
      this.editForm.get('id')?.setValue(this.rowData.id);
      this.editForm.get('customerName')?.setValue(this.rowData.customerName);
      this.editForm.get('itemName')?.setValue(this.rowData.itemName);
      const date = _moment(this.rowData['deliveryDate'], 'YYYY-MM-DD');
      // @ts-ignore
      this.editForm.get('deliveryDate')?.setValue(date);
      this.editForm.get('orderQuantity')?.setValue(this.rowData.orderQuantity);
      this.editForm.get('customerName')?.disable();
      this.editForm.get('itemName')?.disable();
    }
    if (['view'].includes(this.screenMode)) {
      this.editForm.disable();
    }
  }

  Filter = (d: Date): boolean => {
    const day = d.getDay();
    return day !== 0 && day !== 6;
  };

  isAddMode = () => this.screenMode === 'add';
  isEditMode = () => this.screenMode === 'edit';
  isViewMode = () => this.screenMode === 'view';

  ngOnInit(): void {
    this.loadAllCustomerRecords();
    this.loadAllBomRecords();
  }

  updateCustomerDetails(customerName: any): void {
    const product = this.CustomerData.find((customer: any) => customer.firstName === customerName);
    if (product) {
      this.editForm.patchValue(product);
    }
  }

  onCustomerNameChange(): void {
    const selectedCustomerName = this.editForm.get('customerName')?.value;
    this.updateCustomerDetails(selectedCustomerName);
  }

  loadAllCustomerRecords(): void {
    this.customerService.fetchAllRecords().subscribe({
      next: (res: any[]) => {
        if (res.length > 0) {
          this.CustomerData = res;
        } else {
          console.log('No records found');
        }
      },
      error(error) {
        console.error('Error fetching Customer data:', error);
      },
    });
  }

  updateItemDetails(productName: any): void {
    const item = this.ItemData.find((items: any) => items.productName === productName);
    if (item) {
      this.editForm.patchValue({
        itemName: item.productName,
        unitOfMeasure: item.unitOfMeasure,
      });
    }
  }

  onItemNameChange(): void {
    const selectedItemName = this.editForm.get('itemName')?.value;
    this.updateItemDetails(selectedItemName);
  }

  loadAllBomRecords(): void {
    this.bomService.fetchAllBomRecords().subscribe({
      next: (res: any[]) => {
        if (res.length > 0) {
          this.ItemData = res;
        } else {
          console.log('No BOM records found');
        }
      },
      error(error) {
        console.error('Error fetching BOM data:', error);
      },
    });
  }

  save(): void {
    if (this.editForm.invalid) {
      this.editForm.markAllAsTouched();
      return;
    }
    this.isSaving = true;
    const sales = { ...this.rowData, ...this.editForm.getRawValue() };
    sales.createdBy = this.loginUserName;
    if (sales.id !== null) {
      this.salesOrderService.update(sales).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      this.salesOrderService.create(sales).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    }
  }

  previousState(): void {
    this.router.navigate(['sales-order']);
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }
}
