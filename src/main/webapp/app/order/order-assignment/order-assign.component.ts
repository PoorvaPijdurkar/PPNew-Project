/* eslint-disable @typescript-eslint/member-ordering */
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SkuService } from '../../sku/service/sku.service';
import { HttpResponse } from '@angular/common/http';
import { OrderService } from '../service/order.service';
import _moment from 'moment';
import { MomentDateAdapter, MAT_MOMENT_DATE_ADAPTER_OPTIONS } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { UserManagementService } from '../../admin/user-management/service/user-management.service';
import { forkJoin } from 'rxjs';

export const MY_FORMATS = {
  parse: {
    dateInput: 'L',
  },
  display: {
    dateInput: 'L',
  },
};

@Component({
  selector: 'jhi-order-assign',
  templateUrl: './order-assign.component.html',
  styleUrls: ['./order-assign.component.scss'],

  providers: [
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS],
    },
    { provide: MAT_DATE_FORMATS, useValue: MY_FORMATS },
  ],
})
export class OrderAssignComponent implements OnInit {
  selected: any;
  screenMode = 'add';
  isSaving = false;
  rowData: any = {};
  userData: any = {};
  displayedColumns: string[] = ['position', 'name', 'qty', 'deleteAndEditData'];
  skuData: any;

  isLoading = false;
  quantityRequired = 100;
  orderNumber = '';
  errorMessage = '';
  remainingQty = this.quantityRequired;

  dataSource: any[] = [];
  originalDataSource: any[] = [];
  editForm: FormGroup | undefined;
  showEditFields = false;

  constructor(
    private router: Router,
    private skuService: SkuService,
    private orderService: OrderService,
    private userManagementService: UserManagementService
  ) {
    this.dataSource = [];
    const extraParam: any = this.router.getCurrentNavigation()?.extras;
    if (![undefined, null].includes(extraParam)) {
      const stateData = extraParam['state'];
      this.screenMode = stateData['mode'];
      this.rowData = stateData['rowData'];
      this.orderNumber = this.rowData['orderNumber'];
      this.skuData = stateData['skuData'];
      this.quantityRequired = this.rowData.quantityRequired;
      this.editForm = new FormGroup({
        userNames: new FormControl(null, { validators: [Validators.required] }),
        qty: new FormControl(null, {
          validators: [
            Validators.required,
            Validators.max(this.quantityRequired),
            this.remainingQuantity.bind(this),
            this.newQty.bind(this),
          ],
        }),
      });
      console.log('sku data', this.skuData);
      console.log('rowData data', this.rowData);
    }
  }

  remainingQuantity(control: FormControl) {
    const remainingQty = this.quantityRequired - this.calculateTotal();
    return remainingQty >= 0 ? null : { remainingQuantityError: true };
  }

  newQty(control: FormControl) {
    const newQuantity = control.value;
    const remainingQty = this.quantityRequired - this.calculateTotal();
    return newQuantity <= remainingQty ? null : { newQtyExceedsRemaining: true };
  }

  Filter = (d: Date): boolean => {
    const day = d.getDay();
    return day !== 0 && day !== 6;
  };

  save(): void {
    this.isSaving = true;
    const orderAssignment: any = this.editForm?.getRawValue();
    const orderAssignmentData: {
      id: null;
      orderNumber: any;
      user: any;
      status: string;
      updatedBy: string;
      assignedQuantity: any;
      createdBy: string;
      createdDate: string;
      assignedBy: string;
      assignedTo: string;
    }[] = [];
    if (this.dataSource.length > 0) {
      this.dataSource.forEach(item => {
        if (![undefined, null].includes(item)) {
          orderAssignmentData.push({
            id: item.id,
            orderNumber: this.rowData.orderNumber,
            user: item.name,
            status: 'ASSIGNED',
            updatedBy: 'xyz',
            assignedQuantity: item.qty,
            createdBy: 'xyz',
            createdDate: '2023-09-13',
            assignedBy: '5',
            assignedTo: item.name.id,
          });
        }
      });
    }
    if (orderAssignmentData.length > 0) {
      this.orderService.saveAllOrder(orderAssignmentData).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    }
  }

  previousState(): void {
    this.router.navigate(['order']);
  }

  ngOnInit(): void {
    this.dataSource = [];
    this.loadAllData();
  }

  cancel(): void {
    this.router.navigate(['/order']);
  }

  loadAllData(): void {
    forkJoin([
      this.userManagementService.fetchUserByRole('ROLE_FLD_OFFICER'),
      this.orderService.fetchAllOrdersAssigned(this.orderNumber),
    ]).subscribe(results => {
      this.isLoading = false;
      this.userData = results[0];
      const orderAssData: any[] = results[1];
      // @ts-ignore
      let indexNum = 1;
      if (orderAssData.length > 0) {
        orderAssData.forEach(item => {
          this.dataSource.push({
            position: indexNum,
            id: item.id,
            // @ts-ignore
            name: item.user,
            qty: item.assignedQuantity,
            deleteAndEditData: null,
          });
          indexNum++;
        });
        this.dataSource = [...this.dataSource];
        this.originalDataSource = [...this.dataSource];
        console.log(' data souce', this.dataSource);
      } else {
        return;
      }
    });
  }

  loadUserData(): void {
    this.userManagementService.fetchUserByRole('ROLE_FLD_OFFICER').subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.isLoading = false;
        this.userData = res;
      },
      error: () => (this.isLoading = false),
    });
  }

  edit(element: any) {
    console.log('Element User Names:', element.name?.login);
    console.log('User Data:', this.userData);

    if (this.editForm?.get('userNames') && this.editForm.get('qty')) {
      this.editForm.get('userNames')?.setValue(element.name?.login ?? null);
      this.editForm.get('qty')?.setValue(element.qty ?? null);
    }
    this.showEditFields = true;
  }

  add(): void {
    if (!this.editForm?.invalid) {
      const indexNum = this.dataSource.length + 1;
      const user = this.editForm?.get('userNames')?.value;
      const newQuantity = this.editForm?.get('qty')?.value;
      const remainingQty = this.quantityRequired - this.calculateTotal();

      if (newQuantity <= remainingQty && newQuantity > 0) {
        this.dataSource.push({
          position: indexNum,
          id: null,
          name: user ? user : null,
          qty: newQuantity,
          deleteAndEditData: null,
        });
        this.dataSource = [...this.dataSource];
        this.resetForm();
      } else {
        this.errorMessage = 'Quantity is greater than remaining quantity';
      }
    }
  }

  resetForm(): void {
    this.editForm?.get('qty')?.setValue(null);
    this.editForm?.get('userNames')?.setValue(null);
    this.editForm?.reset();
  }

  delete(element: any): void {
    const index = this.dataSource.indexOf(element);
    if (index >= 0) {
      this.dataSource.splice(index, 1);
      this.updateDataSourcePosition();
      this.dataSource = [...this.dataSource];
    }
  }

  updateDataSourcePosition(): void {
    let index = 1;
    this.dataSource.forEach(item => {
      item.position = index;
      index++;
    });
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }

  calculateTotal(): any {
    if (this.dataSource.length > 0) {
      const totalValue = this.dataSource
        .filter(item => item.qty)
        .map(item => parseFloat(item.qty))
        .reduce((sum, current) => sum + current);
      return totalValue;
    }
    return 0;
  }
}
