import { Component, OnInit } from '@angular/core';
import { Account } from '../../core/auth/account.model';
import { OrderService } from '../../order/service/order.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AccountService } from '../../core/auth/account.service';
import { forkJoin } from 'rxjs';
import { SkuService } from '../../sku/service/sku.service';
import { ActionRendererOrderComponent } from './action-renderer.component';

@Component({
  selector: 'jhi-order-assignment-list',
  templateUrl: './order-assignment-list.component.html',
  styleUrls: ['./order-assignment-list.component.scss'],
})
export class OrderAssignmentListComponent implements OnInit {
  pageTitle = 'Orders Assignments';
  public gridApi: any;
  public gridColumnApi: any;
  public columnDefs: any;
  public defaultColDef: any;
  public rowData: any[] = [];
  isLoading = false;
  frameworkComponents: any;
  roles: string[] | undefined = [];

  account: Account | null = null;

  skuList: any[] = [];
  showAddButton: boolean = false;
  constructor(
    private orderService: OrderService,
    private skuService: SkuService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal,
    private accountService: AccountService
  ) {
    this.frameworkComponents = {
      actionRenderer: ActionRendererOrderComponent,
    };
    this.columnDefs = [
      {
        field: 'orderNumber',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'order.sku.productName',
        headerName: 'Name Of Item',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'order.sku.productCode',
        headerName: 'Item code',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'assignedQuantity',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'purchaseQuantity',
        filter: 'agTextColumnFilter',
      },

      {
        field: 'order.ratePrice',
        headerName: 'Rate / Bid Price',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'purchasePrice',
        headerName: 'Purchase Price',
        filter: 'agTextColumnFilter',
      },

      {
        field: 'order.estimatedDate',
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
          label: 'purchase',
          action: 'purchase',
          roles: this.accountService.getUserData()?.authorities || [],
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
    console.log('authorities', this.accountService.getUserData()?.authorities || []);
  }

  onGridReady(params: any) {
    this.gridApi = params.api;
    this.gridColumnApi = params.columnApi;
    this.loadAll();
  }

  loadAll(): void {
    this.skuList = [];
    this.rowData = [];
    this.gridApi.setRowData([]);
    // @ts-ignore
    const userId = this.accountService.getUserData()?.['id'] || null;
    if (![null, undefined].includes(userId)) {
      this.isLoading = true;
      forkJoin([this.orderService.fetchAllOrdersAssignedByUserId(userId)]).subscribe(results => {
        this.isLoading = false;
        this.rowData = results[0];
        // @ts-ignore
        this.orderService.fetchAllRecords().subscribe({
          next: (res: any[]) => {
            this.rowData.forEach(orderAssignment => {
              const orderTemp = res.filter(item => item.orderNumber === orderAssignment.orderNumber);
              if (orderTemp.length > 0) {
                orderAssignment.order = orderTemp[0];
              }
            });
            this.isLoading = false;
            console.log('row data ', this.rowData);
            this.gridApi.setRowData(this.rowData);
          },
          error: () => (this.isLoading = false),
        });
      });
    }
  }

  onActionClicked(e: any) {
    console.log('edi data', e);
    if (![undefined, null].includes(e)) {
      switch (e.action) {
        case 'purchase':
          this.goToPurchase(e.rowData);
          break;
        default:
          break;
      }
    }
  }

  getRoleData() {
    return this.accountService.getUserData()?.authorities || [];
  }
  goToPurchase(rowData: any) {
    this.router.navigate(['orderAssignment/purchase'], {
      state: {
        mode: 'assign',
        rowData: rowData,
        skuData: this.skuList,
      },
    });
  }
}
