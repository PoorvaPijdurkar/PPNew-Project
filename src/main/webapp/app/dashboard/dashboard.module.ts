import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatNativeDateModule } from '@angular/material/core';
import { DashboardComponent } from './dashboard.component';
import { NgxDaterangepickerMd } from 'ngx-daterangepicker-material';
import { OrderStatisticsComponent } from 'app/order-statistics/order-statistics.component';
import { MatTabsModule } from '@angular/material/tabs';
import { PieChartComponent } from 'app/pie-chart/pie-chart.component';

@NgModule({
  declarations: [DashboardComponent, OrderStatisticsComponent, PieChartComponent],

  imports: [
    CommonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    FormsModule,
    ReactiveFormsModule,
    MatTabsModule,
    NgxDaterangepickerMd.forRoot({
      separator: ' - ',
      applyLabel: 'Apply',
    }),
    RouterModule.forChild([
      {
        path: 'dashboard',
        component: DashboardComponent,
      },
    ]),
    SharedModule,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class DashboardModule {}
