import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderAssignmentListComponent } from './order-assignment-list/order-assignment-list.component';
import { OrderAssignmentAddUpdateComponent } from './order-assignment-add-update/order-assignment-add-update.component';
import { OrderAssignmentDeleteComponent } from './order-assignment-delete/order-assignment-delete.component';
import { SharedModule } from '../shared/shared.module';
import { AgGridModule } from 'ag-grid-angular';
import { RouterModule } from '@angular/router';
import { ActionRendererOrderComponent } from './order-assignment-list/action-renderer.component';
import { MatCardModule } from '@angular/material/card';

@NgModule({
  declarations: [
    OrderAssignmentListComponent,
    OrderAssignmentAddUpdateComponent,
    OrderAssignmentDeleteComponent,
    ActionRendererOrderComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    MatCardModule,
    AgGridModule.withComponents([ActionRendererOrderComponent]),
    RouterModule.forChild([
      {
        path: '',
        component: OrderAssignmentListComponent,
      },
      {
        path: 'add',
        component: OrderAssignmentAddUpdateComponent,
      },
      {
        path: 'edit',
        component: OrderAssignmentAddUpdateComponent,
      },
      {
        path: 'view',
        component: OrderAssignmentAddUpdateComponent,
      },
      {
        path: 'purchase',
        component: OrderAssignmentAddUpdateComponent,
      },
    ]),
    SharedModule,
  ],
})
export class OrderAssignmentModule {}
