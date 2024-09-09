import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderPurchasedListComponent } from './order-purchased-list/order-purchased-list.component';
import { RouterModule } from '@angular/router';
import { AgGridModule } from 'ag-grid-angular';
import { ColDef } from 'ag-grid-community';
import { ActionRendererComponent } from './order-purchased-list/action-renderer.component';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { MatCardModule } from '@angular/material/card';
import { OrderPurchaseModificationDialogComponent } from './order-purchase-modification/order-purchase-modification.dialog.component';
import { OrderPurchasedApproveComponent } from './order-purchased-approve/order-purchased-approve.component';

@NgModule({
  declarations: [
    OrderPurchasedListComponent,
    ActionRendererComponent,
    OrderPurchaseModificationDialogComponent,
    OrderPurchasedApproveComponent,
  ],
  imports: [
    CommonModule,
    MatInputModule,
    FormsModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatCardModule,
    AgGridModule.withComponents([ActionRendererComponent]),
    RouterModule.forChild([
      {
        path: '',
        component: OrderPurchasedListComponent,
      },
    ]),
    SharedModule,
  ],
  entryComponents: [],
})
export class OrderPurchasedModule {}
