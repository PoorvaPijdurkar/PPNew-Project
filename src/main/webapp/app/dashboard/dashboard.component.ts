/* eslint-disable @typescript-eslint/restrict-plus-operands */
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { DateAdapter } from '@angular/material';
import { MatDatepicker } from '@angular/material/datepicker';
import { OrderMapping } from 'app/order-statistics/order-statistics-enum';
import { OrderStatistics } from 'app/order-statistics/order-statistics.models';
import * as Highcharts from 'highcharts';
import { DashboardService } from './service/dashboard.service';
import _moment from 'moment';
import dayjs from 'dayjs';
import { forkJoin } from 'rxjs';

export const MY_FORMATS = {
  parse: {
    dateInput: 'LL',
  },
  display: {
    dateInput: 'YYYY-MM-DD',
    monthYearLabel: 'YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'YYYY',
  },
};

@Component({
  selector: 'jhi-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  orderStatisticsData: OrderStatistics[] = [];
  selected: any;
  startDate!: Date;
  endDate!: Date;
  selectedDate!: Date;
  perData: any;
  date = new FormControl(new Date());
  serializedDate = new FormControl(new Date().toISOString());
  @ViewChild('picker', { static: false }) datepicker!: MatDatepicker<any>;
  @ViewChild('hiddenInput') hiddenInput!: ElementRef;

  ordersCount = 0;
  ordersCompleted = 0;
  ordersCancelled = 0;
  ordersPending = 0;
  ranges: any = {
    Today: [dayjs(), dayjs()],
    Yesterday: [dayjs().subtract(1, 'days'), dayjs().subtract(1, 'days')],
    'Last 7 Days': [dayjs().subtract(6, 'days'), dayjs()],
    'Last 30 Days': [dayjs().subtract(29, 'days'), dayjs()],
    'This Month': [dayjs().startOf('month'), dayjs().endOf('month')],
    'Last Month': [dayjs().subtract(1, 'month').startOf('month'), dayjs().subtract(1, 'month').endOf('month')],
  };
  statusData: any;
  constructor(private dateAdapter: DateAdapter<Date>, private dashboardService: DashboardService) {
    this.dateAdapter.setLocale('en-GB');
    this.datepicker = {} as MatDatepicker<any>;
    this.selectedDate = new Date();
  }

  resetDateRange(): void {
    const today = dayjs();
    this.selected = {
      startDate: today.startOf('day').toDate(),
      endDate: today.endOf('day').toDate(),
    };
    this.loadAllData();
  }
  applyDateRange(): void {
    console.log('Selected Date Range:', this.selected);

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
    this.orderStatisticsData[0].totalOrder = 0;
    this.orderStatisticsData[1].totalOrder = 0;
    this.orderStatisticsData[2].totalOrder = 0;
    this.orderStatisticsData[3].totalOrder = 0;
    this.orderStatisticsData[4].totalOrder = 0;
    this.perData = null;
    // const currentDate = _moment(this.selected).format('YYYY-MM-DD');
    // eslint-disable-next-line prefer-const
    const startDate = _moment(this.selected.startDate.$d).format('YYYY-MM-DD');
    const endDate = _moment(this.selected.endDate.$d).format('YYYY-MM-DD');
    forkJoin([
      this.dashboardService.fetchOrdersDetailsInRange(startDate, endDate),
      this.dashboardService.fetchOrdersDetailsInBetween(startDate, endDate),
      this.dashboardService.fetchPercentageForRange(startDate, endDate),
    ]).subscribe(results => {
      let statusData = results[0];
      statusData = results[1];
      this.perData = results[2];
      console.log('Fetched percentage data:', this.perData);

      this.orderStatisticsData[0].totalOrder = statusData.ordersCount;
      this.orderStatisticsData[1].totalOrder = statusData.ordersCancelled;
      this.orderStatisticsData[2].totalOrder = 0;
      this.orderStatisticsData[3].totalOrder = statusData.ordersCompleted;
      this.orderStatisticsData[4].totalOrder = statusData.ordersPending;
      const yAxisData = results[0].map((item: { productName: any }) => item['productName']);
      //         this.renderChart(yAxisData, results[0]);
      if (yAxisData.length > 0 && results[0].length > 0) {
        this.renderChart(yAxisData, results[0]);
      } else {
        console.warn('No data to display, skipping chart rendering');
      }
    });
  }

  ngOnInit(): void {
    this.selected = {
      startDate: dayjs().startOf('day').toDate(),
      endDate: dayjs().endOf('day').toDate(),
    };
    this.orderStatisticsData = this.getOrdersCategoryData();
    this.loadAllData();
  }

  renderChart(yAxisData: any[], chartData: any[]): void {
    const totalCount: number[] = [];
    const completedCount: number[] = [];
    const waitingApprovalCount: number[] = [];
    const failedCount: number[] = [];
    const inProgressCount: number[] = [];

    for (let index = 0; index < yAxisData.length; index++) {
      const element = chartData[index];
      totalCount.push(element['totalOrders']);
      completedCount.push(element['ordersCompleted']);
      // waitingApprovalCount.push(element['ordersInProgress']);
      failedCount.push(element['ordersCancelled']);
      // inProgressCount.push(element['ordersInProgress']);
      inProgressCount.push(element['ordersPending']);
    }

    const chartOptions: Highcharts.Options = {
      chart: {
        type: 'bar',
      },
      title: {
        text: 'SkuWise Split',
      },
      xAxis: {
        categories: yAxisData,
        width: '20px',
      },
      yAxis: {
        min: 0,
        title: {
          text: 'Total',
        },
        tickPositions: [0, 25, 50, 75, 100],
      },
      legend: {
        reversed: true,
      },
      plotOptions: {
        series: {
          stacking: 'normal',
          dataLabels: {
            enabled: true,
            format: '{point.y}',
            style: {
              textOutline: 'none',
              fontWeight: '500',
              color: 'black',
              fontSize: '14px',
            },
            filter: {
              property: 'y',
              operator: '>',
              value: 0,
            },
          },
        },
      },
      series: [
        {
          type: 'bar',
          name: 'Total Order',
          data: totalCount,
          color: '#92D2FF',
        },
        {
          type: 'bar',
          name: 'Completed',
          data: completedCount,
          color: '#7AB59A',
        },
        {
          type: 'bar',
          name: 'waiting Approval',
          data: waitingApprovalCount,
          color: '#b08637',
        },
        {
          type: 'bar',
          name: 'Failed',
          data: failedCount,
          color: '#FEB39C',
        },
        {
          type: 'bar',
          name: 'In Progress',
          data: inProgressCount,
          color: '#FFD151',
        },
      ],
    };

    Highcharts.chart('container', chartOptions);
  }

  // eslint-disable-next-line @typescript-eslint/explicit-function-return-type
  onClick(arg0: string | undefined) {
    throw new Error('Method not implemented.');
  }

  getOrdersCategoryData(): Array<OrderStatistics> {
    const orderStatisticsData: OrderStatistics[] = [
      {
        totalOrder: 0,
        percentage: 0,
        orderType: OrderMapping.TOTAL_ORDER,
        orderCategory: 'TOTAL_ORDER',
        color: '#92D2FF',
      },
      {
        totalOrder: 0,
        percentage: 0,
        orderType: OrderMapping.FAILED,
        orderCategory: 'FAILED',
        color: '#FEB39C',
      },
      {
        totalOrder: 0,
        percentage: 0,
        orderType: OrderMapping.WAITING_APPROVAL,
        orderCategory: 'WAITING_APPROVAL',
        color: '#b08637',
      },
      {
        totalOrder: 0,
        percentage: 0,
        orderType: OrderMapping.COMPLETED_ORDER,
        orderCategory: 'COMPLETED_ORDER',
        color: '#7AB59A',
      },
      {
        totalOrder: 0,
        percentage: 0,
        orderType: OrderMapping.PROGRESS,
        orderCategory: 'PROGRESS',
        color: '#FFD151',
      },
    ];
    return orderStatisticsData;
  }
}
