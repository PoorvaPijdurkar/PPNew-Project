import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeptAddUpdateComponent } from './dept-add-update/dept-add-update.component';
import { RouterModule } from '@angular/router';
import { DeptListComponent } from './dept-list/dept-list.component';
import { DeptDeleteComponent } from './dept-delete/dept-delete.component';
import { ActionRendererComponent } from './dept-list/action-renderer.component';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ReactiveFormsModule } from '@angular/forms';
import { AgGridModule } from 'ag-grid-angular';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [DeptListComponent, DeptAddUpdateComponent, DeptDeleteComponent, ActionRendererComponent],
  imports: [
    CommonModule,
    MatInputModule,
    FormsModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    AgGridModule.withComponents([ActionRendererComponent]),
    RouterModule.forChild([
      {
        path: '',
        component: DeptListComponent,
      },
      {
        path: 'add',
        component: DeptAddUpdateComponent,
      },
      {
        path: 'edit',
        component: DeptAddUpdateComponent,
      },
      {
        path: 'view',
        component: DeptAddUpdateComponent,
      },
    ]),
    SharedModule,
  ],
  entryComponents: [DeptDeleteComponent],
})
export class DeptModule {}
