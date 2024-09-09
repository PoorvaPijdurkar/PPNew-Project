import { CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatNativeDateModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatTabsModule } from '@angular/material/tabs';
import { RouterModule } from '@angular/router';
import { AgGridModule } from 'ag-grid-angular';
import { NgxDaterangepickerMd } from 'ngx-daterangepicker-material';
import { SharedModule } from '../shared/shared.module';
import { ActionRendererComponent } from './order-deatils/action-renderer.component';
import { OrderDeatilsComponent } from './order-deatils/order-deatils.component';
import { OrdersSummaryComponent } from './orders-summary-details/orders-summary.component';

// ModuleRegistry.registerModules([
//   MasterDetailModule,
// ]);

@NgModule({
  declarations: [OrdersSummaryComponent, OrderDeatilsComponent, ActionRendererComponent],
  imports: [
    SharedModule,
    AgGridModule.withComponents([ActionRendererComponent]),
    CommonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
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
        path: 'summary',
        component: OrdersSummaryComponent,
      },
    ]),
    SharedModule,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class OrdersSummaryModule {}
