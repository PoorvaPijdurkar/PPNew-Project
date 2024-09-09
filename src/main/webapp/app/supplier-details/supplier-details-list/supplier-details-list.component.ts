import { Component, OnInit } from '@angular/core';
import { SupplierDetailsService } from '../service/supplier-details.service';
import { ActionRendererComponent } from './action-renderer.component';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { SupplierDetailsDeleteComponent } from '../supplier-details-delete/supplier-details-delete.component';
import { OrderService } from '../../order/service/order.service';

@Component({
  selector: 'jhi-supplier-details-list',
  templateUrl: './supplier-details-list.component.html',
  styleUrls: ['./supplier-details-list.component.scss'],
})
export class SupplierDetailsListComponent implements OnInit {
  select: any;
  pageTitle = 'Supplier Details';
  public gridApi: any;
  public gridColumnApi: any;
  public columnDefs: any;
  public defaultColDef: any;
  public rowData: any;
  isLoading = false;
  frameworkComponents: any;
  constructor(
    private router: Router,
    private supplierDetailsService: SupplierDetailsService,
    private orderService: OrderService,
    private modalService: NgbModal,
    private activatedRoute: ActivatedRoute
  ) {
    this.frameworkComponents = {
      actionRenderer: ActionRendererComponent,
    };
    this.columnDefs = [
      {
        field: 'supplierType',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'firstName',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'lastName',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'emailId',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'address',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'mobileNumber',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'gstNumber',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'totalLandHolding',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'action',
        headerName: 'Action',
        cellRenderer: 'actionRenderer',
        maxWidth: 100,
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
      minWidth: 150,
      filter: 'agTextColumnFilter',
    };
  }

  ngOnInit(): void {}

  onGridReady(params: any): void {
    this.gridApi = params.api;
    this.gridColumnApi = params.columnApi;
    this.loadAll();
  }

  onActionClicked(e: any) {
    console.log('edit data', e);
    if (![undefined, null].includes(e)) {
      switch (e.action) {
        case 'add':
          this.goToSupplierAdd();
          break;
        case 'edit':
          this.goToSupplierEdit(e.rowData);
          break;
        case 'delete':
          this.deleteUser(e.rowData['id']);
          break;
        default:
          this.goToSupplierView(e.rowData);
          break;
      }
    }
  }

  loadAll(): void {
    this.isLoading = true;
    this.rowData = [];
    this.gridApi.setRowData([]);
    this.supplierDetailsService.fetchAllRecords().subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.isLoading = false;
        this.rowData = res;
        this.gridApi.setRowData(this.rowData);
      },
      error: () => (this.isLoading = false),
    });
  }

  goToSupplierAdd() {
    this.router.navigate(['supplier/add'], {
      state: {
        mode: 'add',
      },
    });
  }

  goToSupplierEdit(rowData: any) {
    this.router.navigate(['supplier/edit'], {
      state: {
        mode: 'edit',
        rowData: rowData,
      },
    });
  }

  goToSupplierView(rowData: any) {
    this.router.navigate(['supplier/view'], {
      state: {
        mode: 'view',
        rowData: rowData,
      },
    });
  }

  deleteUser(supplier: any): void {
    const modalRef = this.modalService.open(SupplierDetailsDeleteComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.supplier = supplier;
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
