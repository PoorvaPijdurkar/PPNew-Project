import { Component, OnInit } from '@angular/core';
import { ProductionService } from '../service/production.service';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { ActionRendererComponent } from './action-renderer.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ProductionDeleteComponent } from '../production-delete/production-delete.component';
import { saveAs } from 'file-saver';

@Component({
  selector: 'jhi-production-list',
  templateUrl: './production-list.component.html',
  styleUrls: ['./production-list.component.scss'],
})
export class ProductionListComponent implements OnInit {
  pageTitle = 'Production Reports';
  public gridApi: any;
  public gridColumnApi: any;
  public columnDefs: any;
  public defaultColDef: any;
  public rowData: any;
  isLoading = false;
  frameworkComponents: any;
  roles: string[] = [];
  params: any;
  action: any;
  label: string | undefined;

  constructor(
    private productionService: ProductionService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal
  ) {
    this.frameworkComponents = {
      actionRenderer: ActionRendererComponent,
    };
    this.columnDefs = [
      {
        headerName: 'Item Name',
        field: 'bom.productName',
        filter: 'agTextColumnFilter',
      },
      {
        headerName: 'Batch Number',
        field: 'batchNumber',
        filter: 'agTextColumnFilter',
      },
      {
        headerName: 'Batch Size',
        field: 'batchSize',
        filter: 'agTextColumnFilter',
      },
      {
        headerName: 'Weighing done by',
        field: 'createdBy',
        filter: 'agTextColumnFilter',
      },
      {
        headerName: 'Date',
        field: 'createdDate',
        filter: 'agTextColumnFilter',
      },

      {
        field: 'action',
        headerName: 'Action',
        cellRenderer: 'actionRenderer',
        maxWidth: 120,
        filter: false,
        sortable: false,
        resizable: false,
        cellRendererParams: {
          onClick: this.onActionClicked.bind(this),
          label: 'Click 2',
          action: 'add',
        },
      },
    ];
    this.defaultColDef = {
      sortable: true,
      resizable: true,
      flex: 1,
      minWidth: 70,
      filter: 'agTextColumnFilter',
    };
  }

  ngOnInit(): void {}

  onGridReady(params: any): void {
    this.gridApi = params.api;
    this.gridColumnApi = params.columnApi;
    this.loadAll();
  }

  onActionClicked(e: any): void {
    if (![undefined, null].includes(e)) {
      switch (e.action) {
        case 'add':
          this.goToProductionAdd();
          break;
        case 'delete':
          this.deleteProduction(e.rowData['id']);
          break;
        case 'print':
          this.onGenerateReport(e.rowData.batchNumber);
          break;
        default:
          this.goToProductionView(e.rowData);
          break;
      }
    }
  }

  loadAll(): void {
    this.isLoading = true;
    this.rowData = [];
    this.gridApi.setRowData([]);
    this.productionService.fetchAllRecords().subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.isLoading = false;
        this.rowData = res;
        this.gridApi.setRowData(this.rowData);
      },
      error: () => (this.isLoading = false),
    });
  }

  goToProductionAdd(): void {
    this.router.navigate(['production/add'], {
      state: {
        mode: 'add',
      },
    });
  }

  goToProductionView(rowData: any): void {
    this.router.navigate(['production/view'], {
      state: {
        mode: 'view',
        rowData,
      },
    });
  }

  deleteProduction(production: any): void {
    const modalRef = this.modalService.open(ProductionDeleteComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.production = production;
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
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

  onGenerateReport(batchNumber: string): void {
    this.generateProductionReport(batchNumber);
  }
}
