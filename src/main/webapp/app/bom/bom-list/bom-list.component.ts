import { AllModules } from '@ag-grid-enterprise/all-modules';
import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {
  ColDef,
  GetServerSideGroupKey,
  GridReadyEvent,
  ICellRendererParams,
  IServerSideDatasource,
  IServerSideGetRowsParams,
  IsServerSideGroup,
} from 'ag-grid-community';
import { BomDeleteComponent } from '../bom-delete/bom-delete.component';
import { BOMService } from '../service/bom.service';
import { ActionRendererComponent } from './action-renderer.component';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-bom-list',
  templateUrl: './bom-list.component.html',
  styleUrls: ['./bom-list.component.scss'],
})
export class BomListComponent {
  pageTitle = 'BOM Details';
  public gridApi: any;
  public modules: any[] = AllModules;
  public gridColumnApi: any;
  isLoading = false;
  frameworkComponents: any;

  public columnDefs: ColDef[] = [
    { field: 'id', hide: true },
    { field: 'productName', hide: true },
    { field: 'productCode' },
    { field: 'productType' },
    { field: 'bomLevel' },
    { field: 'unitOfMeasure' },
    { field: 'quantity', valueFormatter: parmas => `${parmas.value} %` },
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
        action: 'bom',
      },
    },
  ];

  public defaultColDef: ColDef = {
    width: 240,
    flex: 1,
    sortable: false,
  };

  public autoGroupColumnDef: ColDef = {
    field: 'productName',
    headerName: 'Product Name',
    cellRendererParams: {
      innerRenderer(params: ICellRendererParams): any {
        return params.data.productName;
      },
    },
  };

  public rowModelType: any = 'serverSide';

  public isServerSideGroup: IsServerSideGroup = (dataItem: any): any => dataItem.skus && dataItem.skus.length;

  public getServerSideGroupKey: GetServerSideGroupKey = (dataItem: any) => dataItem.id;

  public rowData!: any[];
  public themeClass = 'ag-theme-alpine';

  constructor(private router: Router, private modalService: NgbModal, private bomService: BOMService) {
    this.frameworkComponents = {
      actionRenderer: ActionRendererComponent,
    };
  }

  onGridReady(params: GridReadyEvent): any {
    this.gridApi = params.api;
    this.gridColumnApi = params.columnApi;
    this.loadAll(params);
  }

  loadAll(params?: GridReadyEvent): void {
    this.isLoading = true;
    this.rowData = [];
    params!.api.setServerSideDatasource(this.getDataSouces());
  }

  onActionClicked(e: any): void {
    if (![undefined, null].includes(e)) {
      switch (e.action) {
        case 'edit':
          this.navigateToBomScreen(e.action, e.rowData);
          break;
        case 'delete':
          this.deleteUser(e.rowData['id']);
          break;
        case 'view':
          this.navigateToBomScreen(e.action, e.rowData);
          break;
        default:
          break;
      }
    }
  }

  navigateToBomScreen(mode: string, rowData: any) {
    this.router.navigate([`bom/${mode}`], {
      state: {
        mode: mode,
        rowData: rowData,
      },
    });
  }

  goToBomAdd(): void {
    this.router.navigate(['bom/add'], {
      state: {
        mode: 'add',
      },
    });
  }

  deleteUser(bom: any): void {
    const modalRef = this.modalService.open(BomDeleteComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bom = bom;
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
    this.gridApi.setServerSideDatasource(this.getDataSouces());
  }

  private getDataSouces(): IServerSideDatasource {
    return {
      getRows: (params: IServerSideGetRowsParams) => {
        const parentId = params.parentNode.data && params.parentNode.data.id;
        if (parentId && ![null, undefined].includes(parentId)) {
          this.bomService.fetchAllProductDataByBomId(parentId).subscribe({
            next: (result: any) => {
              this.isLoading = false;
              const totalRecords = result!.length;
              if (totalRecords && totalRecords > 0) {
                const resultData = {
                  rowData: result,
                  rowCount: totalRecords,
                };
                params.success(resultData);
              } else {
                params.success({ rowData: [], rowCount: 0 });
              }
            },
          });
        } else {
          this.bomService.fetchAllBomProductData().subscribe({
            next: (result: any) => {
              this.isLoading = false;
              const totalRecords = result!.length;
              if (totalRecords && totalRecords > 0) {
                const resultData = {
                  rowData: result,
                  rowCount: totalRecords,
                };
                params.success(resultData);
              } else {
                params.success({ rowData: [], rowCount: 0 });
              }
            },
            error: () => (this.isLoading = false),
          });
        }
      },
    };
  }
}
