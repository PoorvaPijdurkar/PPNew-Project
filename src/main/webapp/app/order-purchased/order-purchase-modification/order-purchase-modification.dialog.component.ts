import { Component, OnInit } from '@angular/core';
import { User } from '../../admin/user-management/user-management.model';
import { UserManagementService } from '../../admin/user-management/service/user-management.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { OrderPurchasedService } from '../service/order-purchased.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'jhi-sku-delete',
  templateUrl: './order-purchase-modification.dialog.component.html',
  styleUrls: ['./order-purchase-modification.dialog.component.scss'],
})
export class OrderPurchaseModificationDialogComponent implements OnInit {
  rowData?: any;

  editForm = new FormGroup({
    acceptedQty: new FormControl(0, {
      validators: [Validators.required, this.validateAcceptedQty.bind(this)],
    }),
    rejectedQty: new FormControl(0, { validators: [Validators.required] }),
  });

  constructor(private orderPurchasedService: OrderPurchasedService, private activeModal: NgbActiveModal) {
    console.log('rowData', this.rowData);
  }

  validateAcceptedQty(control: FormControl): { [key: string]: boolean } | null {
    const quantitySupplied = this.rowData?.quantitySupplied || 0;
    const acceptedQty = control.value || 0;
    if (acceptedQty > quantitySupplied) {
      return { invalidAcceptedQty: true };
    }
    return null;
  }

  updateRejectedQty(): void {
    const acceptedQty = this.editForm.get('acceptedQty')?.value ?? 0;
    const quantitySupplied = this.rowData?.quantitySupplied ?? 0;
    const rejectedQty = (quantitySupplied - acceptedQty).toFixed(2);
    this.editForm.get('rejectedQty')?.setValue(parseFloat(rejectedQty));
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  save(): void {
    if (this.editForm.valid) {
      const supplierFormData: any = this.editForm.getRawValue();
      this.rowData['acceptedQuantity'] = supplierFormData.acceptedQty;
      this.rowData['rejectedQuantity'] = supplierFormData.rejectedQty;

      this.orderPurchasedService.updateOrderPurchase(this.rowData).subscribe(() => {
        this.activeModal.close('success');
      });
    }
  }

  ngOnInit(): void {
    // Initialize form values
    if (this.rowData?.acceptedQty !== undefined) {
      this.editForm.get('acceptedQty')?.setValue(this.rowData.acceptedQty);
    }
  }
}
