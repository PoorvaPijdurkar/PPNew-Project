import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BOMService } from 'app/bom/service/bom.service';
import { AccountService } from 'app/core/auth/account.service';
import { saveAs } from 'file-saver';
import _moment from 'moment';
import { ProductionService } from '../service/production.service';

@Component({
  selector: 'jhi-production-planner',
  templateUrl: './production-planner.component.html',
  styleUrls: ['./production-planner.component.scss'],
})
export class ProductionPlannerComponent implements OnInit {
  displayedColumns: string[] = ['position', 'productName', 'quantity', 'calculatedQty'];
  dataSource: any[] = [];
  bomData: any = [];
  editForm: FormGroup;
  isSaving = false;
  showEditFields = false;
  selected: any;
  screenMode = 'add';
  rowData: any = {};
  selectedElement: any;
  totalBatchSize = 0;
  reportData: any = {};
  itemCode = 0;
  loginUserName = ``;
  mode: string | undefined;
  itemName: string | undefined;
  orderQuantity: number | undefined;

  constructor(
    private productionService: ProductionService,
    private router: Router,
    private bomService: BOMService,
    private accountService: AccountService,
    private route: ActivatedRoute
  ) {
    this.editForm = new FormGroup({
      id: new FormControl(null),
      productName: new FormControl(null, { validators: [Validators.required] }),
      batchSize: new FormControl(0, { validators: [Validators.required, Validators.min(1), Validators.max(9999999)] }),
    });

    const extraParam: any = this.router.getCurrentNavigation()?.extras;
    if (![undefined, null].includes(extraParam)) {
      const stateData = extraParam['state'];
      this.screenMode = stateData['mode'];
      this.rowData = stateData['rowData'];
      this.itemName = stateData['itemName'];
      this.orderQuantity = stateData['orderQuantity'];
    }
    this.loginUserName = this.accountService?.getUserData()?.firstName + ' ' + this.accountService?.getUserData()?.lastName;
  }

  ngOnInit(): void {
    const state = window.history.state;
    if (state) {
      this.itemName = state.itemName;
      this.orderQuantity = state.orderQuantity;
    }
    this.loadAll();
  }

  fetchProductionData(batchNumber: any): void {
    this.productionService.fetchProductionDataByItemCode(batchNumber).subscribe({
      next: (data: any[]) => {
        if (data.length > 0) {
          const firstItem = data[0];
          this.reportData = {
            batchSheet: firstItem.itemName,
            date: _moment(this.rowData.createdDate).format('DD-MM-YYYY'),
            itemName: firstItem.itemName,
            batchcode: this.rowData.batchNumber,
            batchSize: firstItem.batchSize,
            weighing: this.rowData.createdBy,
            itemCode: firstItem.itemCode,
          };
          this.dataSource = data.map(item => ({
            productName: item.productName,
            calculatedQty: item.calculatedQty || 0,
          }));
        } else {
          console.error('No production data available for batchNumber:', batchNumber);
        }
      },
      error(err) {
        console.error('Error fetching production data:', err);
      },
    });
  }

  updateBomDetails(productName: string): void {
    const product = this.bomData.find((bom: any) => bom.productName === productName);
    if (product) {
      this.editForm.patchValue(product);
    }
  }

  onProductNameChange(): void {
    const selectedProductName = this.editForm.get('productName')?.value;
    this.updateBomDetails(selectedProductName);
  }

  loadAll(): void {
    if (![`view`].includes(this.screenMode)) {
      this.bomService.fetchAllBomRecords().subscribe({
        next: (res: any[]) => {
          if (res.length > 0) {
            this.bomData = res;
            this.setSalesOrderValues();
          }
        },
        error: error => console.error('Error fetching BOM data:', error),
      });
    } else {
      this.fetchProductionData(this.rowData.batchNumber);
    }
  }

  setSalesOrderValues(): void {
    if (this.itemName && this.orderQuantity !== undefined) {
      const selectedProduct = this.bomData.find((product: any) => product.productName === this.itemName);
      if (selectedProduct) {
        this.editForm.patchValue({
          productName: selectedProduct,
          batchSize: this.orderQuantity,
        });
        this.editForm.get('productName')?.disable();
        this.editForm.get('batchSize')?.disable();
      }
    }
  }

  refreshTableDataSource(): any {
    let index = 1;
    this.dataSource.forEach((element: { [x: string]: any }) => {
      element['position'] = index++;
    });
    this.dataSource = [...this.dataSource];
  }

  calculate(): void {
    const product = this.editForm.get('productName')?.value;
    const batchSize = parseFloat(this.editForm.get('batchSize')?.value);
    if (product && batchSize > 0) {
      const bomId = product.id;
      if (bomId) {
        this.productionService.fetchGenerateProductionData(bomId, batchSize).subscribe({
          next: (res: any) => {
            this.dataSource = res;
            this.totalBatchSize = batchSize;
            this.refreshTableDataSource();
          },
          error(error) {
            console.error('Error fetching production data:', error);
          },
        });
      }
    }
  }

  save(): void {
    if (this.dataSource.length === 0) {
      console.error('No data to save');
      return;
    }
    const bom = this.editForm.get('productName')?.value;
    const saveData = {
      batchSize: this.editForm.get('batchSize')?.value,
      createdBy: this.loginUserName,
      updatedBy: this.loginUserName,
      bom: {
        id: bom.id,
        productName: bom.productName,
        productCode: bom.productCode,
        productType: bom.productType,
        bomLevel: bom.bomLevel,
        quantity: bom.quantity,
        unitOfMeasure: bom.unitOfMeasure,
        skus: this.dataSource,
      },
    };

    this.productionService.create(saveData).subscribe({
      next: () => this.onSaveSuccess(),
      error: error => {
        console.error('Save Error:', error);
        this.onSaveError();
      },
    });
  }

  generateProductionReport(batchNumber: string): void {
    this.productionService.generateProductionReport(batchNumber).subscribe({
      next(response: any) {
        const blob = new Blob([response], { type: 'application/pdf' });
        saveAs(blob, `${batchNumber}_production_report.pdf`);
      },
      error(error: any) {
        console.error('Error generating production report:', error);
      },
    });
  }

  onGenerateReport(): void {
    this.generateProductionReport(this.rowData.batchNumber);
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

  previousState() {
    this.router.navigate(['production']);
  }
}
