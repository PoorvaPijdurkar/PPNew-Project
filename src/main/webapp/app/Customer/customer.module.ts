import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AgGridModule } from 'ag-grid-angular';
import { ActionRendererComponent } from './customer-list/action-renderer.component';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { MatCardModule } from '@angular/material/card';
import { CustomerListComponent } from './customer-list/customer-list.component';
import { CustomerAddComponent } from './customer-add/customer-add.component';
import { CustomerDeleteComponent } from './customer-delete/customer-delete.component';
import { MatCheckboxModule } from '@angular/material';
import { MatAutocompleteModule } from '@angular/material';

@NgModule({
  declarations: [CustomerListComponent, CustomerAddComponent, CustomerDeleteComponent, ActionRendererComponent],
  imports: [
    CommonModule,
    MatInputModule,
    FormsModule,
    MatCheckboxModule,
    MatAutocompleteModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatCardModule,
    AgGridModule.withComponents([ActionRendererComponent]),
    RouterModule.forChild([
      {
        path: '',
        component: CustomerListComponent,
      },
      {
        path: 'add',
        component: CustomerAddComponent,
      },
      {
        path: 'edit',
        component: CustomerAddComponent,
      },
      {
        path: 'view',
        component: CustomerAddComponent,
      },
    ]),
    SharedModule,
  ],
  entryComponents: [CustomerDeleteComponent],
})
export class CustomerModule {}
