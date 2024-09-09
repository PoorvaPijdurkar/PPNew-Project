import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderListComponent } from '../order/order-list/order-list.component';
import { OrderDeleteComponent } from '../order/order-delete/order-delete.component';
import { OrderAddUpdateComponent } from './order-add-update/order-add-update.component';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../shared/shared.module';
import { AgGridModule } from 'ag-grid-angular';
import { ActionRendererComponent } from './order-list/action-renderer.component';
import { OrderApproveRejectComponent } from './order-approve-reject/order-approve-reject.component';
import { OrderAssignComponent } from './order-assignment/order-assign.component';
import { MatCardModule } from '@angular/material/card';

@NgModule({
  declarations: [
    OrderListComponent,
    OrderDeleteComponent,
    OrderAddUpdateComponent,
    ActionRendererComponent,
    OrderApproveRejectComponent,
    OrderAssignComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    AgGridModule.withComponents([ActionRendererComponent]),
    RouterModule.forChild([
      {
        path: '',
        component: OrderListComponent,
      },
      {
        path: 'add',
        component: OrderAddUpdateComponent,
      },
      {
        path: 'edit',
        component: OrderAddUpdateComponent,
      },
      {
        path: 'view',
        component: OrderAddUpdateComponent,
      },
      {
        path: 'assign',
        component: OrderAssignComponent,
      },
    ]),
    MatCardModule,
  ],
})
export class OrderModule {}
