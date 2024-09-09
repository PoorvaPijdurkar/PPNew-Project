import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { RouterModule } from '@angular/router';
import { AgGridModule } from 'ag-grid-angular';
import { SharedModule } from '../shared/shared.module';
import { BomAddUpdateComponent } from './bom-add-update/bom-add-update.component';
import { BomDeleteComponent } from './bom-delete/bom-delete.component';
import { ActionRendererComponent } from './bom-list/action-renderer.component';
import { BomListComponent } from './bom-list/bom-list.component';

@NgModule({
  declarations: [ActionRendererComponent, BomListComponent, BomAddUpdateComponent, BomDeleteComponent],
  imports: [
    CommonModule,
    MatInputModule,
    MatDividerModule,
    FormsModule,
    MatFormFieldModule,
    MatCardModule,
    MatTableModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatButtonModule,
    AgGridModule.withComponents([ActionRendererComponent]),
    RouterModule.forChild([
      {
        path: '',
        component: BomListComponent,
      },
      {
        path: 'add',
        component: BomAddUpdateComponent,
      },
      {
        path: 'edit',
        component: BomAddUpdateComponent,
      },
      {
        path: 'view',
        component: BomAddUpdateComponent,
      },
    ]),
    SharedModule,
  ],
  entryComponents: [BomDeleteComponent],
})
export class BomModule {}
