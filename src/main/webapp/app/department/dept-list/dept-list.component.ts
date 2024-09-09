import { Component, OnInit } from '@angular/core';
import { DepartmentService } from '../service/dept.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ActionRendererComponent } from './action-renderer.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DeptDeleteComponent } from '../dept-delete/dept-delete.component';

@Component({
  selector: 'jhi-dept-list',
  templateUrl: './dept-list.component.html',
  styleUrls: ['./dept-list.component.scss'],
})
export class DeptListComponent implements OnInit {
  pageTitle = 'Department Details';
  public gridApi: any;
  public gridColumnApi: any;
  public columnDefs: any;
  public defaultColDef: any;
  public rowData: any;
  isLoading = false;
  frameworkComponents: any;
  constructor(
    private router: Router,
    private departmentService: DepartmentService,
    private modalService: NgbModal,
    private activatedRoute: ActivatedRoute
  ) {
    this.frameworkComponents = {
      actionRenderer: ActionRendererComponent,
    };
    this.columnDefs = [
      {
        field: 'departmentName',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'departmentCode',
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

  onGridReady(params: any) {
    this.gridApi = params.api;
    this.gridColumnApi = params.columnApi;
    this.loadAll();
  }

  onActionClicked(e: any) {
    console.log('edit data', e);
    if (![undefined, null].includes(e)) {
      switch (e.action) {
        case 'add':
          this.goToDeptAdd();
          break;
        case 'edit':
          this.goToDeptEdit(e.rowData);
          break;
        case 'delete':
          this.deleteUser(e.rowData['departmentId']);
          break;
        default:
          this.goToDeptView(e.rowData);
          break;
      }
    }
  }

  loadAll(): void {
    this.isLoading = true;
    this.rowData = [];
    this.gridApi.setRowData([]);
    this.departmentService.fetchAllRecords().subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.isLoading = false;
        this.rowData = res;
        this.gridApi.setRowData(this.rowData);
      },
      error: () => (this.isLoading = false),
    });
  }

  goToDeptAdd() {
    this.router.navigate(['department/add'], {
      state: {
        mode: 'add',
      },
    });
  }

  goToDeptEdit(rowData: any) {
    this.router.navigate(['department/edit'], {
      state: {
        mode: 'edit',
        rowData: rowData,
      },
    });
  }

  goToDeptView(rowData: any) {
    this.router.navigate(['department/view'], {
      state: {
        mode: 'view',
        rowData: rowData,
      },
    });
  }

  deleteUser(department: any): void {
    const modalRef = this.modalService.open(DeptDeleteComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.department = department;
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
