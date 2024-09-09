import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ReactiveFormsModule } from '@angular/forms';
import { AgGridModule } from 'ag-grid-angular';
import { SharedModule } from 'app/shared/shared.module';
import { SalesOrderListComponent } from './sales-order-list/sales-order-list.component';
import { ActionRendererComponent } from './sales-order-list/action-renderer.component';
import { SalesOrderDeleteComponent } from './sales-order-delete/sales-order-delete.component';
import { SalesOrderAddComponent } from './sales-order-add/sales-order-add.component';
import { MatCardModule } from '@angular/material';

@NgModule({
  declarations: [ActionRendererComponent, SalesOrderListComponent, SalesOrderDeleteComponent, SalesOrderAddComponent],
  imports: [
    CommonModule,
    MatCardModule,
    MatInputModule,
    FormsModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    AgGridModule.withComponents([ActionRendererComponent]),
    RouterModule.forChild([
      {
        path: '',
        component: SalesOrderListComponent,
      },
      {
        path: 'add',
        component: SalesOrderAddComponent,
      },
      {
        path: 'edit',
        component: SalesOrderAddComponent,
      },
      {
        path: 'view',
        component: SalesOrderAddComponent,
      },
    ]),
    SharedModule,
  ],
  entryComponents: [SalesOrderDeleteComponent],
})
export class SalesOrderModule {}
