/* eslint-disable @typescript-eslint/restrict-plus-operands */
import { Component, OnInit, OnChanges, Input } from '@angular/core';
import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';

@Component({
  selector: 'jhi-region-wise-split-chart',
  templateUrl: './region-wise-split-chart.component.html',
  styleUrls: ['./region-wise-split-chart.component.scss'],
})
export class RegionWiseSplitChartComponent implements OnInit, OnChanges {
  @Input() skuWiseData: any;

  lineChartOptions: object = {};
  chartSeriesEvent = '';
  regionName: any[] = [];
  itemsPerPage: number = ITEMS_PER_PAGE;

  // eslint-disable-next-line @typescript-eslint/no-empty-function
  constructor() {}
  ngOnInit(): void {
    if (![null, undefined].includes(this.skuWiseData)) {
      this.loadChartData(this.skuWiseData);
    }
  }

  ngOnChanges(): void {
    if (![null, undefined].includes(this.skuWiseData)) {
      this.loadChartData(this.skuWiseData);
    }
  }

  onChartSeriesEvent(event: any): void {
    this.chartSeriesEvent = 'you have clicked on series' + event.context.name + ' in group: ' + event.originalEvent.point.category;
  }

  loadChartData(skuData: any): void {
    const skuName: any = [];
    const skuValue: any[] = [];
    if (![null, undefined].includes(skuData)) {
      const skuValueTemp = {
        name: '',
        data: [],
      };

      skuData.forEach((item: any) => {
        const accSystem = item.accSystem ? item.accSystem : '';
        const accSysSku = item.accSysSku ? item.accSysSku : '';
        let skuNameTemp = '';
        if (![null, '', undefined].includes(accSystem)) {
          skuNameTemp = accSystem;
        }
        if (![null, '', undefined].includes(accSysSku)) {
          skuNameTemp = skuNameTemp + ' ' + accSysSku;
        }
        skuName.push(skuNameTemp);
        // skuValueTemp.data.push(item.accSysAndSkuCount);
      });
      skuValue.push(skuValueTemp);
    }
    this.lineChartOptions = {
      chart: {
        type: 'bar',
        zoomType: 'xy',
      },
      title: {
        text: '',
      },
      legend: {
        enabled: true,
        fontSize: '12px',
      },
      colors: ['#4396B8', '#B0D3E2'],
      xAxis: {
        categories: [...skuName],
        crossHair: {
          enabled: true,
        },
        min: '0',
        max: 'null',
        scrollbar: {
          enabled: true,
        },
        labels: {
          useHTML: true,
          style: {
            color: '#666666',
            fontSize: 'small',
          },
        },
      },
      plotOptions: {
        bar: {
          showInLegend: false,
          dataLabels: {
            enabled: true,
            color: '#191970',
            style: {
              fontSize: '11px',
              fontWeight: 'thin',
              fontFamily: 'Arial',
              textOutine: false,
            },
          },
        },
        series: {
          cursor: 'pointer',
        },
      },
      yAxis: {
        lineWidth: 0,
        minorGridLineWidth: 0,
        lineColor: 'transparent',
        gridLineWidth: 1,
        tickInterval: 25,
        title: {
          text: '',
        },
        scrollbar: {
          enabled: false,
        },
      },
      tooltip: {
        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        pointFormat: '<tr><td style="padding:0"Total Orders: </td></tr>',
        footerFormat: '</table>',
        shared: true,
        useHTML: true,
      },
      series: skuValue,
    };
  }
}
