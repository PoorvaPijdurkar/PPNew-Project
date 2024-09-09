import { Component, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import _moment from 'moment';
import dayjs from 'dayjs';
import { DateAdapter } from '@angular/material';
import { forkJoin } from 'rxjs';
import { DashboardService } from '../../dashboard/service/dashboard.service';

@Component({
  selector: 'jhi-orders-summary',
  templateUrl: './orders-summary.component.html',
  styleUrls: ['./orders-summary.component.scss'],
})
export class OrdersSummaryComponent implements OnInit {
  selected: any;
  selectedDate!: Date;
  result: any;
  selectedTabIndex: any;
  panelData: any = [];
  currentSelectedCard: any;
  orderDetails: any;

  ranges: any = {
    Today: [dayjs(), dayjs()],
    Yesterday: [dayjs().subtract(1, 'days'), dayjs().subtract(1, 'days')],
    'Last 7 Days': [dayjs().subtract(6, 'days'), dayjs()],
    'Last 30 Days': [dayjs().subtract(29, 'days'), dayjs()],
    'This Month': [dayjs().startOf('month'), dayjs().endOf('month')],
    'Last Month': [dayjs().subtract(1, 'month').startOf('month'), dayjs().subtract(1, 'month').endOf('month')],
  };

  isLoading = false;
  frameworkComponents: any;
  limitPerPage = 20;
  totalPages = 0;
  currentPage = 0;
  tabsData: any = [
    {
      requestCategory: 'PROGRESS',
      label: 'In Progress',
      icon: 'timelapse',
      index: 0,
      statusFilter: { 'inProgress.greaterThan': 0 },
      count: { prev: 0, curr: 0 },
      color: '#0685f4',
      status: 'WAITING FOR APPROVAL',
      summaryData: { visitedPageData: new Map(), totalPages: 0 },
    },
    {
      requestCategory: 'FAILED',
      label: 'Failed',
      icon: 'warning',
      index: 1,
      statusFilter: { 'failure.greaterThan': 0 },
      count: { prev: 0, curr: 0 },
      color: '#FEB39C',
      status: 'REJECTED',
      summaryData: { visitedPageData: new Map(), totalPages: 0 },
    },
    {
      requestCategory: 'WAITING_APPROVAL',
      label: 'Waiting Approval',
      icon: 'edit',
      index: 2,
      statusFilter: { 'waitingApproval.greaterThan': 0 },
      count: { prev: 0, curr: 0 },
      color: '#b08637',
      status: '',
      summaryData: { visitedPageData: new Map(), totalPages: 0 },
    },
    {
      requestCategory: 'COMPLETED',
      label: 'Completed',
      icon: 'warning',
      index: 3,
      statusFilter: { 'completed.greaterThan': 0 },
      status: 'APPROVED',
      count: { prev: 0, curr: 0 },
      color: '#7AB59A',
      summaryData: { visitedPageData: new Map(), totalPages: 0 },
    },
    {
      requestCategory: 'TOTAL',
      label: 'Total Orders',
      icon: 'edit',
      status: 'ALL',
      index: 4,
      statusFilter: { 'total.greaterThan': 0 },
      count: { prev: 0, curr: 0 },
      color: '#92D2FF',
      summaryData: { visitedPageData: new Map(), totalPages: 0 },
    },
  ];
  onActionClicked: any;
  fetchedOrderDetails: any;

  constructor(private dateAdapter: DateAdapter<Date>, private dashboardService: DashboardService) {
    this.selectedDate = new Date();
  }

  resetDateRange(): void {
    this.selected = {
      startDate: dayjs().startOf('day').toDate(),
      endDate: dayjs().endOf('day').toDate(),
    };
    this.currentSelectedCard = null;
    this.loadAllData();
  }

  applyDateRange(): void {
    if (
      ![undefined, null].includes(this.selected) &&
      ![undefined, null].includes(this.selected.startDate) &&
      ![undefined, null].includes(this.selected.endDate)
    ) {
      this.loadAllData();
    } else {
      console.warn('Invalid date range selected');
    }
  }

  loadAllData(): void {
    // this.panelData = [];
    const startDate = _moment(this.selected.startDate.$d).format('YYYY-MM-DD');
    const endDate = _moment(this.selected.endDate.$d).format('YYYY-MM-DD');
    console.log('startDate' + startDate);
    console.log('endDate' + endDate);
    const status = this.tabsData.filter((item: { index: any }) => item.index === this.selectedTabIndex)[0].status;
    const page = 0;
    const size = 8;
    forkJoin([
      this.dashboardService.fetchOrdersDetailsInBetween(startDate, endDate),
      this.dashboardService.fetchOrdersSummaryDetailsInRange(startDate, endDate, status, page, size),
    ]).subscribe(results => {
      const statusData = results[0];
      this.panelData = results[1];

      this.tabsData[0].count.prev = this.tabsData[0].count.curr;
      this.tabsData[1].count.prev = this.tabsData[1].count.curr;
      this.tabsData[2].count.prev = this.tabsData[2].count.curr;
      this.tabsData[3].count.prev = this.tabsData[3].count.curr;
      this.tabsData[4].count.prev = this.tabsData[4].count.curr;

      this.tabsData[0].count.curr = statusData.ordersPending;
      this.tabsData[1].count.curr = statusData.ordersCancelled;
      this.tabsData[3].count.curr = statusData.ordersCompleted;
      this.tabsData[4].count.curr = statusData.ordersCount;
      if (this.panelData.length > 0) {
        this.passDataToChild(this.panelData[0]);
      }
    });
  }

  statusTabChange(event: any): void {
    this.selectedTabIndex = event.index;
    this.loadAllData();
  }

  passDataToChild(data: any): void {
    this.currentSelectedCard = {
      ...data,
      isRowClicked: true,
      tabIndex: this.selectedTabIndex,
    };
    console.log('Selected panel :', this.currentSelectedCard);
  }

  goToPreviousPage(): void {
    if (this.currentPage) {
      this.currentPage = this.currentPage - 1;
    }
  }

  goToNextPage(): void {
    this.currentPage = this.currentPage + 1;
  }

  showDateList(): void {}

  ngOnInit(): void {
    this.selected = {
      startDate: dayjs().startOf('day').toDate(),
      endDate: dayjs().endOf('day').toDate(),
    };
    this.selectedTabIndex = 4;
    this.loadAllData();
  }
}
