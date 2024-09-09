/* eslint-disable @typescript-eslint/member-ordering */
import { Component, OnInit } from '@angular/core';
import { SalesOrderService } from '../service/sales-order.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ActionRendererComponent } from './action-renderer.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { SalesOrderDeleteComponent } from '../sales-order-delete/sales-order-delete.component';

@Component({
  selector: 'jhi-sales-order-list',
  templateUrl: './sales-order-list.component.html',
  styleUrls: ['./sales-order-list.component.scss'],
})
export class SalesOrderListComponent implements OnInit {
  pageTitle = 'Sales Order';
  public gridApi: any;
  public gridColumnApi: any;
  public columnDefs: any;
  public defaultColDef: any;
  public rowData: any;
  isLoading = false;
  frameworkComponents: any;
  constructor(
    private router: Router,
    private salesOrderService: SalesOrderService,
    private modalService: NgbModal,
    private activatedRoute: ActivatedRoute
  ) {
    this.frameworkComponents = {
      actionRenderer: ActionRendererComponent,
    };
    this.columnDefs = [
      {
        field: 'customerName',
        headerName: 'Customer Name',
        filter: 'agTextColumnFilter',
        checkboxSelection: true,
        headerCheckboxSelection: true,
      },
      {
        field: 'itemName',
        headerName: 'Item Name',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'deliveryDate',
        headerName: 'Delivery Date',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'orderQuantity',
        headerName: 'Quantity',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'createdDate',
        headerName: 'Created Date',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'createdBy',
        headerName: 'Created By',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'action',
        headerName: 'Action',
        cellRenderer: 'actionRenderer',
        maxWidth: 130,
        filter: false,
        sortable: false,
        resizable: false,
        cellRendererParams: {
          onClick: this.onActionClicked.bind(this),
          label: 'Click 2',
          action: 'add',
          navigateToProductionAdd: this.navigateToProductionAdd.bind(this),
        },
      },
    ];
    this.defaultColDef = {
      sortable: true,
      resizable: true,
      flex: 1,
      filter: 'agTextColumnFilter',
    };
  }

  ngOnInit(): void {}

  onGridReady(params: any) {
    this.gridApi = params.api;
    this.gridColumnApi = params.columnApi;
    this.loadAll();
  }

  onActionClicked(e: any) {
    if (![undefined, null].includes(e)) {
      switch (e.action) {
        case 'add':
          this.goToSalesOrderAdd();
          break;
        case 'edit':
          this.goToSalesOrderEdit(e.rowData);
          break;
        case 'delete':
          this.deleteSalesOrder(e.rowData['id']);
          break;
        case 'production':
          this.navigateToProductionAdd(e.rowData);
          break;
        default:
          this.goToSalesOrderView(e.rowData);
          break;
      }
    }
  }

  loadAll(): void {
    this.isLoading = true;
    this.rowData = [];
    this.gridApi.setRowData([]);
    this.salesOrderService.fetchAllSalesOrderRecords().subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.isLoading = false;
        this.rowData = res;
        this.gridApi.setRowData(this.rowData);
      },
      error: () => (this.isLoading = false),
    });
  }

  goToSalesOrderAdd() {
    this.router.navigate(['sales-order/add'], { state: { mode: 'add' } });
  }

  goToSalesOrderEdit(rowData: any) {
    this.router.navigate(['sales-order/edit'], { state: { mode: 'edit', rowData } });
  }

  goToSalesOrderView(rowData: any) {
    this.router.navigate(['sales-order/view'], { state: { mode: 'view', rowData } });
  }

  navigateToProductionAdd(rowData: any): void {
    this.router.navigate(['/production/add'], { state: { mode: 'add', itemName: rowData.itemName, orderQuantity: rowData.orderQuantity } });
  }

  deleteSalesOrder(salesOrder: any): void {
    const modalRef = this.modalService.open(SalesOrderDeleteComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.salesOrder = salesOrder;
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
