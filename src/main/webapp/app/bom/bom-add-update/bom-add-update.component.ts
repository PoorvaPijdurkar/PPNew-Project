/* eslint-disable @typescript-eslint/no-unnecessary-condition */
/* eslint-disable @typescript-eslint/member-ordering */
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BOMService } from '../service/bom.service';
import { UOMS } from 'app/app.constants';

@Component({
  selector: 'jhi-bom-add-update',
  templateUrl: './bom-add-update.component.html',
  styleUrls: ['./bom-add-update.component.scss'],
})
export class BomAddUpdateComponent implements OnInit {
  // @ViewChild(MatTable, {static: false}) table : MatTable<any>  // Initialize
  displayedColumns: string[] = [
    'position',
    'productName',
    'productCode',
    'productType',
    'unitOfMeasure',
    'price',
    'quantity',
    'deleteAndEditData',
  ];
  dataSource: any[] = [];
  skuData: any[] = [];
  editForm: FormGroup;
  addProductForm: FormGroup;
  isSaving = false;
  showEditFields = false;
  selected: any;
  screenMode = `view`;
  rowData: any = {};
  totalQuantity = 0;
  unitOfMeasure: string[] = [...UOMS.map(item => item.name)];

  constructor(private bomService: BOMService, private router: Router) {
    this.editForm = new FormGroup({
      id: new FormControl(null),
      qty: new FormControl(null, { validators: [Validators.required] }),
      itemName: new FormControl(null, { validators: [Validators.required] }),
      uom: new FormControl('Kg', { validators: [Validators.required] }),
      prodType: new FormControl('Finished Goods', { validators: [Validators.required] }),
      prodCode: new FormControl(null, { validators: [Validators.required] }),
      bomLevel: new FormControl(0, { validators: [Validators.required] }),
    });

    this.addProductForm = new FormGroup({
      productName: new FormControl(null, { validators: [Validators.required] }),
      quantity: new FormControl(0, { validators: [Validators.required, Validators.min(0)] }),
    });

    const extraParam: any = this.router.getCurrentNavigation()?.extras;
    this.editForm.get('prodType')?.disable();
    if (![undefined, null].includes(extraParam)) {
      const stateData = extraParam['state'];
      this.screenMode = stateData['mode'];
      this.rowData = stateData['rowData'];
      this.skuData = stateData['skuData'];
    }

    if (['edit', 'view'].includes(this.screenMode)) {
      this.editForm.get('qty')?.setValue(this.rowData['quantity']);
      this.editForm.get('itemName')?.setValue(this.rowData['productName']);
      this.editForm.get('uom')?.setValue(this.rowData['unitOfMeasure']);
      this.editForm.get('prodType')?.setValue(this.rowData['productType']);
      this.editForm.get('prodCode')?.setValue(this.rowData['productCode']);
      this.editForm.get('bomLevel')?.setValue(this.rowData['bomLevel']);
    }

    if (['view'].includes(this.screenMode)) {
      this.editForm.get('qty')?.disable();
      this.editForm.get('itemName')?.disable();
      this.editForm.get('uom')?.disable();
      this.editForm.get('prodCode')?.disable();
      this.editForm.get('bomLevel')?.disable();
      this.displayedColumns = [...this.displayedColumns.filter(item => item !== `deleteAndEditData`)];
    }
  }

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll(): void {
    if (['edit', 'view'].includes(this.screenMode)) {
      this.bomService.fetchAllProductDataByBomId(this.rowData.id).subscribe({
        next: (res: any[]) => {
          if (res && res.length > 0) {
            this.dataSource = [...res];
            this.refreshTableDataSource();
          }
        },
        error: error => console.error('Error fetching SKU data:', error),
      });
    } else {
      this.rowData = {
        productName: null,
        productCode: null,
        productType: 'Finished Goods',
        bomLevel: 1,
        quantity: null,
        unitOfMeasure: null,
        skus: [],
      };
    }
    this.bomService.fetchAllSkuByCriteria('Finished-Goods').subscribe({
      next: (res: any[]) => {
        if (res.length > 0) {
          this.skuData = res;
        }
      },
      error: error => console.error('Error fetching SKU data:', error),
    });
  }

  refreshTableDataSource() {
    let index = 1;
    this.dataSource.forEach((element: { [x: string]: any }) => {
      element['position'] = index++;
    });
    this.dataSource = [...this.dataSource];
  }

  updateProductDetails(productName: string): void {
    const product = this.skuData.find((sku: any) => sku.productName === productName);
    if (product) {
      this.editForm.patchValue(product);
    }
  }

  onProductNameChange(): void {
    const selectedProductName = this.editForm.get('productName')?.value;
    this.updateProductDetails(selectedProductName);
  }

  add(): void {
    const productData = this.addProductForm.get('productName')?.value;
    const quantity = parseFloat(this.addProductForm.get('quantity')?.value);
    if (productData && quantity && quantity <= 100) {
      const newData = { ...productData };
      newData['quantity'] = quantity;
      this.dataSource.push(newData);
      this.refreshTableDataSource();
      this.calculateTotalQuantity();
    } else {
      console.error('Product Name and Quantity are required, and Quantity should be 100%');
    }
  }

  resetForm(): void {
    this.addProductForm.get('quantity')?.setValue(0);
  }

  previousState(): void {
    this.router.navigate(['bom']);
  }

  edit(element: any): void {
    if (this.editForm.get('productName') && this.editForm.get('quantity')) {
      //  this.editForm.get('productName')?.setValue(element.productName ?? null);
      this.editForm.get('quantity')?.setValue(element.quantity ?? null);
    }
    this.showEditFields = true;
  }

  delete(element: any): void {
    const index = this.dataSource.indexOf(element);
    if (index >= 0) {
      this.dataSource.splice(index, 1);
      this.updateDataSourcePosition();
      this.dataSource = [...this.dataSource];
      this.calculateTotalQuantity();
    }
  }

  updateDataSourcePosition(): void {
    let index = 1;
    this.dataSource.forEach(item => {
      item.position = index;
      index++;
    });
  }

  calculateTotalQuantity(): number {
    this.totalQuantity = this.dataSource.reduce((sum, item) => sum + parseFloat(item.quantity), 0);
    return this.totalQuantity;
  }

  save(): void {
    this.editForm.markAllAsTouched();
    this.calculateTotalQuantity();
    if (!this.editForm.valid) {
      console.log('Form is invalid. Errors:');
      Object.keys(this.editForm.controls).forEach(key => {
        const controlErrors = this.editForm.get(key)?.errors;
        if (controlErrors != null) {
          console.log(`Key: ${key}, Errors:`, controlErrors);
        }
      });
      return;
    }

    if (this.dataSource.length > 0 && this.totalQuantity === 100) {
      console.log('Saving Data...');
      this.isSaving = true;

      const bomData = { ...this.rowData };
      bomData['quantity'] = this.editForm.get('qty')?.value;
      bomData['productName'] = this.editForm.get('itemName')?.value;
      bomData['productCode'] = this.editForm.get('prodCode')?.value;
      bomData['unitOfMeasure'] = this.editForm.get('uom')?.value;
      bomData['skus'] = this.dataSource.map(item => {
        const sku = this.skuData.find(sku => sku.productName === item.productName && sku.productCode === item.productCode);
        return {
          productName: item.productName,
          productType: item.productType,
          productCode: item.productCode,
          unitOfMeasure: item.unitOfMeasure,
          price: item.price,
          id: sku ? sku.id : undefined,
          quantity: item.quantity,
        };
      });

      this.bomService.createBom(bomData).subscribe({
        next: () => this.onSaveSuccess(),
        error: error => {
          console.error('Save Error:', error);
          this.onSaveError();
        },
      });
    } else {
      console.error('Form is invalid or total quantity is not 100%');
    }
  }

  saveEditData(): void {
    this.isSaving = true;
    const bom = this.editForm.getRawValue();
    if (bom.bomId !== null) {
      // this.bomService.updateBom(bom).subscribe({
      //   next: () => this.onSaveSuccess(),
      //   error: () => this.onSaveError(),
      // });
    } else {
      console.log('bom updated');
    }
  }

  cancel(): void {
    window.history.back();
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }

  isSaveShow() {
    return ['edit', 'add'].includes(this.screenMode);
  }
}
