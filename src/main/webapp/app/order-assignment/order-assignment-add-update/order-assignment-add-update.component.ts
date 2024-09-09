import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { forkJoin } from 'rxjs';
import { OrderService } from '../../order/service/order.service';
import { OrderAssignmentService } from '../service/OrderAssignmentService';

@Component({
  selector: 'jhi-order-assignment-add-update',
  templateUrl: './order-assignment-add-update.component.html',
  styleUrls: ['./order-assignment-add-update.component.scss'],
})
export class OrderAssignmentAddUpdateComponent implements OnInit {
  @Input() orderAssignment: any;
  selected = '';
  isLoading = false;
  screenMode = 'add';
  isSaving = false;
  rowData: any = {};
  supplierTypes = ['Trader', 'Farmer'];

  quantityRequired = -1;

  suppliers: any[] = [
    {
      id: null,
      orderNumber: null,
      supplierType: null,
      firstName: 'Other',
      lastName: null,
      emailId: null,
      address: null,
      mobileNumber: null,
      gstNumber: null,
      totalLandHolding: null,
      quantitySupplied: null,
      quantityRejected: null,
      weightedAverage: null,
      transportationCost: null,
      supplierRatePrice: null,
      quantityReceived: null,
      acceptedQuantity: null,
      rejectedQuantity: null,
      status: null,
      remark: null,
      orders: null,
    },
  ];

  editForm = new FormGroup({
    id: new FormControl(null),
    suppliers: new FormControl(null, {}),
    supplierType: new FormControl('Trader', { validators: [Validators.required] }),
    firstName: new FormControl(null, { validators: [Validators.required, Validators.maxLength(60)] }),
    lastName: new FormControl(null, { validators: [Validators.required, Validators.maxLength(60)] }),
    emailId: new FormControl(null, { validators: [Validators.required, Validators.email] }),
    address: new FormControl(null, { validators: [Validators.required, Validators.maxLength(100)] }),
    mobileNumber: new FormControl(null, {
      validators: [Validators.required, Validators.pattern(/^\d{10}$/), Validators.pattern(/^[0-9]+$/)],
    }),
    gstNumber: new FormControl(null, { validators: [Validators.pattern(/^[0-9]+$/)] }),
    totalLandHolding: new FormControl(null, { validators: [Validators.min(0), Validators.pattern(/^\d+(\.\d{1,2})?$/)] }),
    quantitySupplied: new FormControl(null, {
      validators: [Validators.required, Validators.min(0), Validators.pattern(/^\d+(\.\d{1,2})?$/)],
    }),
    supplierRatePrice: new FormControl(null, {
      validators: [Validators.required, Validators.min(0), Validators.pattern(/^\d+(\.\d{1,2})?$/)],
    }),
    transportationCost: new FormControl(null, {
      validators: [Validators.required, Validators.min(0), Validators.pattern(/^\d+(\.\d{1,2})?$/)],
    }),
    status: new FormControl(null, {}),
    remark: new FormControl(null, {}),
  });

  constructor(private router: Router, private orderAssignmentService: OrderAssignmentService, private orderService: OrderService) {
    const extraParam: any = this.router.getCurrentNavigation()?.extras;
    if (![undefined, null].includes(extraParam)) {
      const stateData = extraParam['state'];
      this.screenMode = stateData.mode || 'add';
      this.rowData = stateData.rowData || {};
      console.log(' row data ----', this.rowData);
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
      this.editForm.get('quantitySupplied')?.disable();
      this.editForm.get('remark')?.disable();
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
      const supplierFormData: any = this.editForm.getRawValue();

      console.log('supplierFormData---', supplierFormData);
      const supplierPurchaseOrderDetails = {
        id: null,
        orderNumber: this.rowData.orderNumber,
        supplierType: supplierFormData?.supplierType,
        firstName: supplierFormData.firstName,
        lastName: supplierFormData.lastName,
        emailId: supplierFormData.emailId,
        address: supplierFormData.address,
        mobileNumber: supplierFormData.mobileNumber,
        gstNumber: supplierFormData.gstNumber,
        totalLandHolding: supplierFormData.totalLandHolding,
        quantitySupplied: supplierFormData.quantitySupplied,
        quantityRejected: null,
        weightedAverage: null,
        transportationCost: supplierFormData.transportationCost,
        supplierRatePrice: supplierFormData.supplierRatePrice,
        quantityReceived: null,
        acceptedQuantity: null,
        rejectedQuantity: null,
        status: null,
        remark: supplierFormData.remark,
        orders: this.rowData,
        supplierDetails: supplierFormData.suppliers,
      };
      if (supplierPurchaseOrderDetails.id !== null) {
        this.orderAssignmentService.updateSupplier(supplierPurchaseOrderDetails).subscribe({
          next: () => this.onSaveSuccess(),
          error: () => this.onSaveError(),
        });
      } else {
        this.orderAssignmentService.createSupplierPurchaseOrder(supplierPurchaseOrderDetails).subscribe({
          next: () => this.updateSupplierAndOrderAssignmentDetails(),
          error: () => this.onSaveError(),
        });
      }
    }
  }

  updateSupplierAndOrderAssignmentDetails() {
    const supplierFormData: any = this.editForm.getRawValue();
    let quantitySupplied = Number(supplierFormData.quantitySupplied);
    const purchaseQuantity = ![null, undefined, ''].includes(this.rowData.purchaseQuantity) ? Number(this.rowData.purchaseQuantity) : 0;
    this.rowData.purchaseQuantity = purchaseQuantity + quantitySupplied;
    forkJoin([this.orderService.updateAssignedOrder(this.rowData)]).subscribe(results => {
      this.isLoading = false;
      this.rowData = results[0];
      // @ts-ignore
      this.orderService.fetchAllRecords().subscribe({
        next: (res: any[]) => {
          this.isLoading = false;
          this.onSaveSuccess();
        },
        error: () => (this.isLoading = false),
      });
    });
  }
  previousState() {
    //     window.history.back();
    this.router.navigate(['orderAssignment']);
  }

  ngOnInit(): void {
    console.log(this.rowData);
    const purchaseQuantity = ![null, undefined, ''].includes(this.rowData.purchaseQuantity) ? Number(this.rowData.purchaseQuantity) : 0;
    const assignedQuantity = ![null, undefined, ''].includes(this.rowData.assignedQuantity) ? Number(this.rowData.assignedQuantity) : 0;
    this.quantityRequired = assignedQuantity - purchaseQuantity;
    this.loadAllSupplierDetails();
  }

  loadAllSupplierDetails() {
    this.orderAssignmentService.fetchAllSupplierDetails().subscribe({
      next: result => {
        console.log(result);
        this.suppliers = [...result, ...this.suppliers];
      },
      error: () => this.onSaveError(),
    });
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }

  supplierChanged(event: any) {
    console.log('event ', event);
    this.setDataToFormField(event);
  }

  setDataToFormField(supplierData: any) {
    this.editForm.get('id')?.setValue(supplierData.id);
    this.editForm.get('supplierType')?.setValue(supplierData.supplierType);
    if (supplierData.firstName === 'Other') {
      this.editForm.get('firstName')?.setValue(null);
    } else {
      this.editForm.get('firstName')?.setValue(supplierData.firstName);
    }
    this.editForm.get('lastName')?.setValue(supplierData.lastName);
    this.editForm.get('emailId')?.setValue(supplierData.emailId);
    this.editForm.get('address')?.setValue(supplierData.address);
    this.editForm.get('mobileNumber')?.setValue(supplierData.mobileNumber);
    this.editForm.get('gstNumber')?.setValue(supplierData.gstNumber);
    this.editForm.get('totalLandHolding')?.setValue(supplierData.totalLandHolding);
    this.editForm.get('quantitySupplied')?.setValue(supplierData.quantitySupplied);
    this.editForm.get('transportationCost')?.setValue(supplierData.transportationCost);
    this.editForm.get('status')?.setValue(supplierData.status);
    this.editForm.get('remark')?.setValue(supplierData.remark);
  }
}
