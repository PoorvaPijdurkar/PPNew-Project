import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SupplierDetailsAddUpdateComponent } from './supplier-details-add-update/supplier-details-add-update.component';
import { SupplierDetailsListComponent } from './supplier-details-list/supplier-details-list.component';
import { RouterModule } from '@angular/router';
import { AgGridModule } from 'ag-grid-angular';
import { ColDef } from 'ag-grid-community';
import { ActionRendererComponent } from './supplier-details-list/action-renderer.component';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { SupplierDetailsDeleteComponent } from './supplier-details-delete/supplier-details-delete.component';
import { MatCardModule } from '@angular/material/card';

@NgModule({
  declarations: [SupplierDetailsListComponent, SupplierDetailsAddUpdateComponent, ActionRendererComponent, SupplierDetailsDeleteComponent],
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
        component: SupplierDetailsListComponent,
      },
      {
        path: 'add',
        component: SupplierDetailsAddUpdateComponent,
      },
      {
        path: 'edit',
        component: SupplierDetailsAddUpdateComponent,
      },
      {
        path: 'view',
        component: SupplierDetailsAddUpdateComponent,
      },
    ]),
    SharedModule,
  ],
  entryComponents: [SupplierDetailsDeleteComponent],
})
export class SupplierDetailsModule {}
