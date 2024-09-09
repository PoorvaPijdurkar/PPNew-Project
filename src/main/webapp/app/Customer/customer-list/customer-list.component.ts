/* eslint-disable @typescript-eslint/explicit-function-return-type */
import { Component, OnInit } from '@angular/core';
import { CustomerService } from '../service/customer.service';
import { ActionRendererComponent } from './action-renderer.component';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { CustomerDeleteComponent } from '../customer-delete/customer-delete.component';

@Component({
  selector: 'jhi-customer-list',
  templateUrl: './customer-list.component.html',
  styleUrls: ['./customer-list.component.scss'],
})
export class CustomerListComponent implements OnInit {
  select: any;
  pageTitle = 'Customer Details';
  public gridApi: any;
  public gridColumnApi: any;
  public columnDefs: any;
  public defaultColDef: any;
  public rowData: any;
  isLoading = false;
  frameworkComponents: any;
  constructor(
    private router: Router,
    private customerService: CustomerService,
    private modalService: NgbModal,
    private activatedRoute: ActivatedRoute
  ) {
    this.frameworkComponents = {
      actionRenderer: ActionRendererComponent,
    };
    this.columnDefs = [
      {
        field: 'firstName',
        headerName: 'First Name ',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'lastName',
        headerName: 'Last Name ',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'companyName',
        headerName: 'Company Name ',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'address',
        headerName: 'Address ',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'addressLine1',
        headerName: 'Address Line-1 ',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'addressLine2',
        headerName: 'Address Line-2 ',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'city',
        headerName: 'City ',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'pin',
        headerName: 'PIN',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'state',
        headerName: 'State',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'country',
        headerName: 'Country',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'email',
        headerName: 'E-Mail',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'mobileNo',
        headerName: 'Mobile Number ',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'gstNo',
        headerName: 'GST Number',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'leadSource',
        headerName: 'Lead source',
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
    if (![undefined, null].includes(e)) {
      switch (e.action) {
        case 'add':
          this.goToCustomerAdd();
          break;
        case 'edit':
          this.goToCustomerEdit(e.rowData);
          break;
        case 'delete':
          this.deleteCustomer(e.rowData['id']);
          break;
        default:
          this.goToCustomerView(e.rowData);
          break;
      }
    }
  }

  loadAll(): void {
    this.isLoading = true;
    this.rowData = [];
    this.gridApi.setRowData([]);
    this.customerService.fetchAllRecords().subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.isLoading = false;
        this.rowData = res;
        this.gridApi.setRowData(this.rowData);
      },
      error: () => (this.isLoading = false),
    });
  }

  goToCustomerAdd() {
    this.router.navigate(['customer/add'], {
      state: {
        mode: 'add',
      },
    });
  }

  goToCustomerEdit(rowData: any) {
    this.router.navigate(['customer/edit'], {
      state: {
        mode: 'edit',
        rowData,
      },
    });
  }

  goToCustomerView(rowData: any) {
    this.router.navigate(['customer/view'], {
      state: {
        mode: 'view',
        rowData,
      },
    });
  }

  deleteCustomer(customer: any): void {
    const modalRef = this.modalService.open(CustomerDeleteComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.customer = customer;
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
