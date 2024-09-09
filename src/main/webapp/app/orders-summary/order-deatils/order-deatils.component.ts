import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { DashboardService } from 'app/dashboard/service/dashboard.service';
import { OrderService } from 'app/order/service/order.service';
import { FirstDataRenderedEvent, IDetailCellRendererParams } from 'ag-grid-community';

@Component({
  selector: 'jhi-order-deatils',
  templateUrl: './order-deatils.component.html',
  styleUrls: ['./order-deatils.component.scss'],
})
export class OrderDeatilsComponent implements OnInit, OnChanges {
  @Input() currentSelectedCard: any;
  public gridApi: any;
  public gridColumnApi: any;
  detailRowData: [] = [];
  public columnDefs = [
    //     {
    //       field: 'sku',
    //       filter: 'agGroupCellRenderer',
    //     },
    {
      field: 'orderNumber',
      filter: 'agTextColumnFilter',
      cellRenderer: 'agGroupCellRenderer',
    },
    {
      field: 'ratePrice',
      filter: 'agTextColumnFilter',
    },
    {
      field: 'quantityRequired',
      filter: 'agTextColumnFilter',
    },
    {
      field: 'orderDate',
      filter: 'agTextColumnFilter',
    },
    {
      field: 'estimatedDate',
      filter: 'agTextColumnFilter',
    },
  ];
  defaultColDef = {
    flex: 1,
  };
  rowData: any;
  isLoading = false;
  frameworkComponents: any;
  public detailColumnDefs: any;
  detailCellRenderer: any;

  public detailCellRendererParams: any = {
    detailGridOptions: {
      columnDefs: [
        {
          field: 'orderNumber',
          filter: 'agTextColumnFilter',
        },
        {
          field: 'purchaseQuantity',
          filter: 'agTextColumnFilter',
        },
        {
          field: 'bidRate',
          filter: 'agTextColumnFilter',
        },
        {
          field: 'createdDate',
          filter: 'agTextColumnFilter',
        },
        {
          field: 'assignedQuantity',
          filter: 'agTextColumnFilter',
        },
        {
          field: 'createdBy',
          filter: 'agTextColumnFilter',
        },
      ],
      defaultColDef: {
        flex: 1,
      },
    },
    getDetailRowData: (params: any) => {
      //       this.loadDetailTableData(params);
    },
  };
  // public rowData! : OrdertData[];

  constructor(private dashboardService: DashboardService, private orderService: OrderService) {}

  onFirstDataRendered(params: FirstDataRenderedEvent): void {
    setTimeout(() => {
      params.api.getDisplayedRowAtIndex(1)!.setExpanded(true);
    }, 0);
  }

  //   loadDetailTableData(rowData: any): void {
  //     this.detailRowData = [];
  //
  //     const orderNumber = '20240109105137';
  //     this.dashboardService.fetchOrderAssignmentDetails(orderNumber).subscribe((resultData: any) => {
  //       if (![undefined, null].includes(resultData)) {
  //         this.detailRowData = resultData;
  //         // eslint-disable-next-line @typescript-eslint/no-unnecessary-condition
  //         if (this.detailRowData) {
  //           rowData.successCallback(this.detailRowData);
  //         } else {
  //           rowData.successCallback();
  //         }
  //       }
  //     });
  //   }

  onGridReady(params: any): void {
    this.gridApi = params.api;
    this.gridColumnApi = params.columnApi;
    console.log('called onGridReady');

    // const orderNumber = this.currentSelectedCard.orderNumber;
    // this.dashboardService.fetchOrderAssignmentDetails(orderNumber).subscribe(data => {
    //   console.log(data);
    //   this.rowData = data;
    // });
    this.loadAll();
  }

  onRowClicked(params: any): void {
    console.log('onRowClicked', params);
  }

  onCellClicked(params: any): void {
    console.log('onCellClicked', params);
    this.gridApi.setRowNodeExpanded(params.node, !params.node.expanded);
  }

  loadAll(): void {
    this.isLoading = true;
    this.rowData = [];
    this.gridApi.setRowData([]);

    const orderNumber = this.currentSelectedCard.orderNumber;
    this.orderService.fetchAllOrderByOrderNumber(orderNumber).subscribe((response: any) => {
      this.rowData = response;
      this.gridApi.setRowData(this.rowData);
      this.gridApi.forEachNode((node: any) => {
        if (node.rowIndex === 0) {
          node.setExpanded(true);
          this.gridApi.getFirstDisplayedRow();
          this.gridApi.gridOptionsWrapper.gridOptions.detailCellRendererParams.getDetailRowData();
        }
      });
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  ngOnChanges(changes: SimpleChanges): void {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-condition
    if (changes.currentSelectedCard) {
      this.currentSelectedCard = changes.currentSelectedCard.currentValue;
      this.loadAll();
    }
  }
}
