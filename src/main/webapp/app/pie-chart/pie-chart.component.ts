import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import _moment from 'moment';
import * as Highcharts from 'highcharts';

@Component({
  selector: 'jhi-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.scss'],
})
export class PieChartComponent implements OnInit, OnChanges {
  selected: any;
  startDate!: Date;
  endDate!: Date;
  selectedDate!: Date;
  @Input() data: any;
  Highcharts = Highcharts;
  chart: Highcharts.Chart | undefined;

  ngOnInit(): void {
    this.renderPieChart();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.data && changes.data.currentValue) {
      this.renderPieChart();
    }
  }

  prepareChartData(resultData: any): any[] {
    if (resultData && resultData.length > 0) {
      return resultData.map((item: any) => {
        return { name: item['product_Type'], y: item['percentage'] };
      });
    }
    return [];
  }

  renderPieChart(): void {
    const chartData: any[] = this.prepareChartData(this.data);
    if (chartData.length > 0) {
      const chartOptions: Highcharts.Options = {
        colors: ['#8BC1F7', '#F4C145', '#7CC674', '#EC7A08', '#A2C3DB', '#8AAF22', '#FF9655', '#FFF263', '#6AF9C4', '#8481DD'],
        chart: {
          type: 'pie',
        },
        title: {
          text: 'Product Type Wise',
        },
        series: [
          {
            type: 'pie',
            name: 'Quantity',
            data: [...chartData] as Highcharts.PointOptionsObject[],
            dataLabels: {
              enabled: true,
              distance: -50,
              format: '{point.name}<br/><b>{point.percentage:.2f} % </b>',
              style: {
                textOutline: 'none',
                fontWeight: 'normal',
                color: 'black',
              },
            },
          },
        ],
      };
      if (this.chart) {
        this.chart.destroy();
      }

      this.chart = Highcharts.chart('pieChart', chartOptions);
    } else {
      console.warn('No data to display, skipping pie chart rendering');
    }
  }
}
