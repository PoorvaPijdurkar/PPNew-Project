import { Component, OnInit, Input } from '@angular/core';
import { OrderMapping } from './order-statistics-enum';
import { OrderStatistics } from './order-statistics.models';

@Component({
  selector: 'jhi-order-statistics',
  templateUrl: './order-statistics.component.html',
  styleUrls: ['./order-statistics.component.scss'],
})
export class OrderStatisticsComponent implements OnInit {
  @Input()
  orderStatistics!: OrderStatistics;

  selectedCategory: any;
  orderMapping: any = OrderMapping;
  color!: string;

  // eslint-disable-next-line @typescript-eslint/no-empty-function
  constructor() {}

  ngOnInit(): void {
    if (this.orderStatistics && this.orderStatistics['orderCategory']) {
      this.selectedCategory = this.orderMapping[this.orderStatistics['orderCategory']];
      this.color = this.orderStatistics!.color!;
    }
  }
}
