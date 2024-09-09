import { Component, OnInit } from '@angular/core';
import { SkuService } from '../service/sku.service';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { ActionRendererComponent } from './action-renderer.component';
import { AccountService } from 'app/core/auth/account.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SkuDeleteDialogComponent } from '../sku-delete/sku-delete.dialog.component';

@Component({
  selector: 'jhi-sku-list',
  templateUrl: './sku-list.component.html',
  styleUrls: ['./sku-list.component.scss'],
})
export class SkuListComponent implements OnInit {
  pageTitle = 'SKU Details';
  public gridApi: any;
  public gridColumnApi: any;
  public columnDefs: any;
  public defaultColDef: any;
  public rowData: any;
  isLoading = false;
  frameworkComponents: any;
  types = ['EXCEL', 'CSV'];
  roles: string[] = [];
  params: any;
  action: any;
  label: string | undefined;

  constructor(
    private skuService: SkuService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal,
    public accountService: AccountService
  ) {
    this.frameworkComponents = {
      actionRenderer: ActionRendererComponent,
    };
    this.columnDefs = [
      {
        headerName: 'Product Name',
        field: 'productName',
        filter: 'agTextColumnFilter',
      },
      {
        headerName: 'Product Code',
        field: 'productCode',
        filter: 'agTextColumnFilter',
      },
      {
        headerName: 'Product Type',
        field: 'productType',
        filter: 'agTextColumnFilter',
      },
      {
        headerName: 'Unit Of Measure',
        field: 'unitOfMeasure',
        filter: 'agTextColumnFilter',
      },
      {
        headerName: 'Price',
        field: 'price',
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

  ngOnInit(): void {
    this.accountService.identity().subscribe();
    this.router.events.subscribe(event => {
      // eslint-disable-next-line no-empty
      if (event instanceof NavigationEnd) {
      }
    });
  }

  onGridReady(params: any): void {
    this.gridApi = params.api;
    this.gridColumnApi = params.columnApi;
    this.loadAll();
  }

  onActionClicked(e: any): void {
    if (![undefined, null].includes(e)) {
      switch (e.action) {
        case 'add':
          this.goToSkuAdd();
          break;
        case 'edit':
          this.goToSkuEdit(e.rowData);
          break;
        case 'delete':
          this.deleteUser(e.rowData['id']);
          break;
        default:
          this.goToSkuView(e.rowData);
          break;
      }
    }
  }

  loadAll(): void {
    this.isLoading = true;
    this.rowData = [];
    this.gridApi.setRowData([]);
    this.skuService.fetchAllRecords().subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.isLoading = false;
        this.rowData = res;
        this.gridApi.setRowData(this.rowData);
      },
      error: () => (this.isLoading = false),
    });
  }

  goToSkuAdd(): void {
    this.router.navigate(['sku/add'], {
      state: {
        mode: 'add',
      },
    });
  }

  goToSkuEdit(rowData: any): void {
    this.router.navigate(['sku/edit'], {
      state: {
        mode: 'edit',
        rowData,
      },
    });
  }

  goToSkuView(rowData: any): void {
    this.router.navigate(['sku/view'], {
      state: {
        mode: 'view',
        rowData,
      },
    });
  }

  deleteUser(sku: any): void {
    const modalRef = this.modalService.open(SkuDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sku = sku;
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
