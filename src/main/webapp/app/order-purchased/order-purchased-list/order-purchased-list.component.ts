import { Component, OnInit } from '@angular/core';
import { OrderPurchasedService } from '../service/order-purchased.service';
import { ActionRendererComponent } from './action-renderer.component';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AccountService } from '../../core/auth/account.service';
import { OrderPurchaseModificationDialogComponent } from '../order-purchase-modification/order-purchase-modification.dialog.component';
import { OrderPurchasedApproveComponent } from '../order-purchased-approve/order-purchased-approve.component';

@Component({
  selector: 'jhi-order-purchased-list',
  templateUrl: './order-purchased-list.component.html',
  styleUrls: ['./order-purchased-list.component.scss'],
})
export class OrderPurchasedListComponent implements OnInit {
  select: any;
  pageTitle = 'Order-Purchased Details';
  public gridApi: any;
  public gridColumnApi: any;
  public columnDefs: any;
  public defaultColDef: any;
  public rowData: any;
  isLoading = false;
  frameworkComponents: any;
  constructor(
    private router: Router,
    private orderPurchasedService: OrderPurchasedService,
    private modalService: NgbModal,
    private activatedRoute: ActivatedRoute,
    private accountService: AccountService
  ) {
    this.frameworkComponents = {
      actionRenderer: ActionRendererComponent,
    };
    this.columnDefs = [
      {
        headerName: 'Order Number',
        field: 'orderNumber',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'supplierDetails.supplierType',
        headerName: 'Supplier Type',
        filter: 'agTextColumnFilter',
      },
      {
        headerName: 'Name',
        valueGetter(params: any) {
          // eslint-disable-next-line @typescript-eslint/restrict-plus-operands
          return params.data.supplierDetails.firstName + ' ' + params.data.supplierDetails.lastName;
        },
        filter: 'agTextColumnFilter',
      },
      {
        field: 'supplierDetails.emailId',
        headerName: 'Email Id',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'supplierDetails.address',
        headerName: 'Address',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'supplierDetails.mobileNumber',
        headerName: 'Mobile Number',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'supplierDetails.gstNumber',
        headerName: 'Gst Number',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'supplierDetails.totalLandHolding',
        headerName: 'TotalLandHolding',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'quantitySupplied',
        headerName: 'Quantity Supplied',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'weightedAverage',
        headerName: 'Weighted Average',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'transportationCost',
        headerName: 'Transportation Cost',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'supplierRatePrice',
        headerName: 'Supplier RatePrice',
        filter: 'agTextColumnFilter',
      },

      {
        field: 'acceptedQuantity',
        headerName: 'Accepted Quantity',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'rejectedQuantity',
        headerName: 'Rejected Quantity',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'status',
        headerName: 'Status',
        filter: 'agTextColumnFilter',
        cellStyle: (params: { value: string }) => {
          if (params.value === 'APPROVED') {
            return { color: 'green', 'font-weight': 'normal' };
          } else if (params.value === 'WAITING FOR APPROVAL') {
            return { color: 'orange', 'font-weight': 'normal' };
          }
          return null;
        },
      },
      {
        field: 'remark',
        headerName: 'Remark',
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
          label: 'modify',
          action: 'modify',
          roles: this.accountService.getUserData()?.authorities || [],
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

  loadAll(): void {
    this.isLoading = true;
    this.rowData = [];
    this.gridApi.setRowData([]);
    this.orderPurchasedService.fetchAllOrderPurchased().subscribe({
      next: (res: any[]) => {
        this.isLoading = false;
        this.rowData = res;
        console.log('rowData', this.rowData);
        this.gridApi.setRowData(this.rowData);
      },
      error: () => (this.isLoading = false),
    });
  }

  onActionClicked(e: any) {
    console.log('edi data', e);
    if (![undefined, null].includes(e)) {
      switch (e.action) {
        case 'modify':
          this.openOrderPurchaseModification(e?.rowData);
          break;
        case 'approve':
          e.rowData['status'] = 'APPROVED';
          this.approveOrder(e.rowData, 'Approve');
          break;
        default:
          break;
      }
    }
  }

  openOrderPurchaseModification(rowData: any) {
    const modalRef = this.modalService.open(OrderPurchaseModificationDialogComponent, { size: 'xl', backdrop: 'static' });
    modalRef.componentInstance.rowData = rowData;
    modalRef.closed.subscribe(reason => {
      if (reason === 'success') {
        this.loadAll();
      }
    });
  }

  approveOrder(orderPurchase: any, action: string): void {
    const modalRef = this.modalService.open(OrderPurchasedApproveComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.orderPurchase = orderPurchase;
    modalRef.componentInstance.action = action;
    modalRef.closed.subscribe(reason => {
      if (reason) {
        this.loadAll();
      }
    });
  }
}
