/* eslint-disable @typescript-eslint/explicit-function-return-type */
import { Component, OnInit } from '@angular/core';
import { LeadSourceService } from '../service/lead-source.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ActionRendererComponent } from './action-renderer.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { LeadSourceDeleteComponent } from '../lead-source-delete/lead-source-delete.component';

@Component({
  selector: 'jhi-lead-source-list',
  templateUrl: './lead-source-list.component.html',
  styleUrls: ['./lead-source-list.component.scss'],
})
export class LeadSourceListComponent implements OnInit {
  pageTitle = 'Lead Source';
  public gridApi: any;
  public gridColumnApi: any;
  public columnDefs: any;
  public defaultColDef: any;
  public rowData: any;
  isLoading = false;
  frameworkComponents: any;

  constructor(
    private router: Router,
    private leadSourceService: LeadSourceService,
    private modalService: NgbModal,
    private activatedRoute: ActivatedRoute
  ) {
    this.frameworkComponents = {
      actionRenderer: ActionRendererComponent,
    };
    this.columnDefs = [
      {
        field: 'createdDate',
        headerName: 'Created Date',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'sourceType',
        headerName: 'Name',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'description',
        headerName: 'Description',
        filter: 'agTextColumnFilter',
      },
      {
        field: 'status',
        headerName: 'Status',
        filter: 'agTextColumnFilter',
        cellRenderer: this.statusCellRenderer.bind(this),
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
    if (![undefined, null].includes(e)) {
      switch (e.action) {
        case 'add':
          this.goToLeadSourceAdd();
          break;
        case 'edit':
          this.goToLeadSourceEdit(e.rowData);
          break;
        case 'delete':
          this.deleteLeadSource(e.rowData['id']);
          break;
        default:
          this.goToLeadSourceView(e.rowData);
          break;
      }
    }
  }

  loadAll(): void {
    this.isLoading = true;
    this.rowData = [];
    this.gridApi.setRowData([]);
    this.leadSourceService.fetchLeadSourceTypeRecords().subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.isLoading = false;
        this.rowData = res;
        this.gridApi.setRowData(this.rowData);
      },
      error: () => (this.isLoading = false),
    });
  }

  goToLeadSourceAdd() {
    this.router.navigate(['lead-source/add'], {
      state: {
        mode: 'add',
      },
    });
  }

  goToLeadSourceEdit(rowData: any) {
    this.router.navigate(['lead-source/edit'], {
      state: {
        mode: 'edit',
        rowData,
      },
    });
  }

  goToLeadSourceView(rowData: any) {
    this.router.navigate(['lead-source/view'], {
      state: {
        mode: 'view',
        rowData,
      },
    });
  }

  deleteLeadSource(leadSource: any): void {
    const modalRef = this.modalService.open(LeadSourceDeleteComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.leadSource = leadSource;
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }

  statusCellRenderer(params: any) {
    return params.value ? 'Active' : 'Inactive';
  }
}
