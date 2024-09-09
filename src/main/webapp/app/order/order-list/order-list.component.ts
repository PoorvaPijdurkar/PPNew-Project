import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { forkJoin } from 'rxjs';
import { Account } from '../../core/auth/account.model';
import { AccountService } from '../../core/auth/account.service';
import { SkuService } from '../../sku/service/sku.service';
import { OrderApproveRejectComponent } from '../order-approve-reject/order-approve-reject.component';
import { OrderDeleteComponent } from '../order-delete/order-delete.component';
import { OrderService } from '../service/order.service';
import { ActionRendererComponent } from './action-renderer.component';

@Component({
  selector: 'jhi-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.scss'],
})
export class OrderListComponent implements OnInit {
  pageTitle = 'Orders Details';
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
  showAddButton = false;
  constructor(
    private orderService: OrderService,
    private skuService: SkuService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal,
    private accountService: AccountService
  ) {
    this.frameworkComponents = {
      actionRenderer: ActionRendererComponent,
    };
    this.columnDefs = [
      {
        field: 'orderNumber',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'sku.productName',
        headerName: 'Name Of Item',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'sku.productCode',
        headerName: 'Item code',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'quantityRequired',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'ratePrice',
        headerName: 'Rate / Bid Price',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'estimatedDate',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'status',
        filter: 'agTextColumnFilter',
        cellStyle: (params: { value: string }) => {
          if (params.value === 'APPROVED') {
            //mark police cells as red
            return { color: 'green', 'font-weight': 'normal' };
          } else if (params.value === 'WAITING FOR APPROVAL') {
            return { color: 'orange', 'font-weight': 'normal' };
          } else if (params.value === 'REJECTED') {
            // @ts-ignore
            return { color: 'red', 'font-weight': 'normal' };
          }
          return null;
        },
      },

      {
        field: 'action',
        headerName: 'Action',
        cellRenderer: 'actionRenderer',
        minWidth: 150,
        filter: false,
        sortable: false,
        resizable: false,
        cellRendererParams: {
          onClick: this.onActionClicked.bind(this),
          label: 'Click 2',
          action: 'approve',
          roles: this.getRoleData(),
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

  showAddAction() {
    // @ts-ignore
    this.roles?.forEach(role => {
      if (['ROLE_ADMIN', 'ROLE_PRODUCTION_MANAGER', 'ROLE_PRODUCTION_OFFICER'].includes(role)) {
        return true;
      }
    });
    return false;
  }

  getRoleData() {
    return this.accountService.getUserData()?.authorities || [];
  }
  ngOnInit(): void {
    this.roles = this.accountService.getUserData()?.authorities || [];
    // @ts-ignore
    this.roles?.forEach(role => {
      if (['ROLE_ADMIN', 'ROLE_PRODUCTION_MANAGER', 'ROLE_PRODUCTION_OFFICER'].includes(role)) {
        this.showAddButton = true;
      }
    });
    this.accountService.getAuthenticationState().subscribe(account => {
      console.log('Account :: ', this.account);
      this.account = account;
      this.roles = this.accountService.getUserData()?.authorities || [];
    });
  }

  onGridReady(params: any) {
    this.gridApi = params.api;
    this.gridColumnApi = params.columnApi;
    this.loadAll();
  }

  onActionClicked(e: any) {
    console.log('edi data', e);
    if (![undefined, null].includes(e)) {
      switch (e.action) {
        case 'add':
          this.goToOrderAdd();
          break;
        case 'edit':
          this.goToOrderEdit(e.rowData);
          break;
        case 'delete':
          this.deleteOrder(e.rowData['id']);
          break;
        case 'approve':
          e.rowData['status'] = 'APPROVED';
          this.approveRejectOrder(e.rowData, 'Approve');
          break;
        case 'rejected':
          e.rowData['status'] = 'REJECTED';
          this.approveRejectOrder(e.rowData, 'Rejected');
          break;
        case 'assign':
          this.goToAssigned(e.rowData);
          break;
        default:
          this.goToOrderView(e.rowData);
          break;
      }
    }
  }

  loadAll(): void {
    this.isLoading = true;
    this.skuList = [];
    this.rowData = [];
    this.gridApi.setRowData([]);
    forkJoin([this.orderService.fetchAllRecords(), this.skuService.fetchAllRecords()]).subscribe(
      results => {
        this.isLoading = false;
        this.rowData = results[0];
        this.skuList = results[1];
        // @ts-ignore
        if (![undefined, null].includes(this.rowData)) {
          this.rowData.forEach(item => {
            const skuData = this.skuList.filter(sku => sku.id === item.fkSkuId);
            if (skuData.length > 0) {
              item['sku'] = skuData[0];
            }
          });
        } else {
          return;
        }
      },
      error => {
        console.error(error);
      }
    );
  }

  goToOrderAdd() {
    this.router.navigate(['order/add'], {
      state: {
        mode: 'add',
        skuData: this.skuList,
      },
    });
  }

  goToOrderEdit(rowData: any) {
    this.router.navigate(['order/edit'], {
      state: {
        mode: 'edit',
        rowData: rowData,
        skuData: this.skuList,
      },
    });
  }

  goToOrderView(rowData: any) {
    this.router.navigate(['order/view'], {
      state: {
        mode: 'view',
        rowData: rowData,
        skuData: this.skuList,
      },
    });
  }

  goToAssigned(rowData: any) {
    this.router.navigate(['order/assign'], {
      state: {
        mode: 'assign',
        rowData: rowData,
        skuData: this.skuList,
      },
    });
  }

  approveRejectOrder(order: any, action: string): void {
    const modalRef = this.modalService.open(OrderApproveRejectComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.order = order;
    modalRef.componentInstance.action = action;
    modalRef.closed.subscribe(reason => {
      if (reason) {
        this.loadAll();
      }
    });
  }

  deleteOrder(order: any): void {
    const modalRef = this.modalService.open(OrderDeleteComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.order = order;
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
