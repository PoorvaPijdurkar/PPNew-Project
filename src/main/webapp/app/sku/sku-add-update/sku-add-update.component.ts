import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { SkuService } from '../service/sku.service';
import { PRODUCT_TYPES, UOMS } from 'app/app.constants';

@Component({
  selector: 'jhi-sku-add-update',
  templateUrl: './sku-add-update.component.html',
  styleUrls: ['./sku-add-update.component.scss'],
})
export class SkuAddUpdateComponent {
  selected: any;
  unitOfMeasure: string[] = [...UOMS.map(item => item.name)];
  productTypes: string[] = [...PRODUCT_TYPES.map(item => item.name)];

  screenMode = 'add';
  isSaving = false;
  rowData: any = {};
  editForm = new FormGroup({
    id: new FormControl(null),
    productName: new FormControl(null, { validators: [Validators.required, Validators.maxLength(25)] }),
    productCode: new FormControl(null, { validators: [Validators.required, Validators.maxLength(25)] }),
    productType: new FormControl(null, { validators: [Validators.required, Validators.maxLength(25)] }),
    unitOfMeasure: new FormControl(null, { validators: [Validators.required, Validators.maxLength(10)] }),
    price: new FormControl(null, { validators: [Validators.required, Validators.maxLength(25)] }),
  });

  constructor(private router: Router, private skuService: SkuService) {
    const extraParam: any = this.router.getCurrentNavigation()?.extras;
    if (![undefined, null].includes(extraParam)) {
      const stateData = extraParam['state'];
      this.screenMode = stateData['mode'];
      this.rowData = stateData['rowData'];
    }
    if (['edit', 'view'].includes(this.screenMode)) {
      this.editForm.get('id')?.setValue(this.rowData['id']);
      this.editForm.get('productName')?.setValue(this.rowData['productName']);
      this.editForm.get('productCode')?.setValue(this.rowData['productCode']);
      this.editForm.get('productType')?.setValue(this.rowData['productType']);
      this.editForm.get('unitOfMeasure')?.setValue(this.rowData['unitOfMeasure']);
      this.editForm.get('price')?.setValue(this.rowData['price']);
      this.editForm.get('productName')?.disable();
    }
    if (['view'].includes(this.screenMode)) {
      this.editForm.get('id')?.disable();
      this.editForm.get('productName')?.disable();
      this.editForm.get('productCode')?.disable();
      this.editForm.get('productType')?.disable();
      this.editForm.get('unitOfMeasure')?.disable();
      this.editForm.get('price')?.disable();
    }
  }

  isAddMode(): any {
    return this.screenMode === 'add';
  }

  isEditMode(): any {
    return this.screenMode === 'edit';
  }

  isViewMode(): any {
    return this.screenMode === 'view';
  }

  save(): void {
    this.isSaving = true;
    const sku = this.editForm.getRawValue();
    if (sku.id !== null) {
      this.skuService.update(sku).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      this.skuService.create(sku).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    }
  }

  previousState(): any {
    window.history.back();
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }
}
