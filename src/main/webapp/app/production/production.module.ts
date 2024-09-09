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
import { ProductionDeleteComponent } from './production-delete/production-delete.component';
import { ActionRendererComponent } from './production-list/action-renderer.component';
import { ProductionListComponent } from './production-list/production-list.component';
import { ProductionPlannerComponent } from './production-planner/production-planner.component';

@NgModule({
  declarations: [ProductionListComponent, ActionRendererComponent, ProductionDeleteComponent, ProductionPlannerComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    SharedModule,
    MatCardModule,
    MatDividerModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatTableModule,
    AgGridModule.withComponents([ActionRendererComponent]),
    RouterModule.forChild([
      {
        path: '',
        component: ProductionListComponent,
      },
      {
        path: 'add',
        component: ProductionPlannerComponent,
      },
      {
        path: 'view',
        component: ProductionPlannerComponent,
      },
      {
        path: 'production/add',
        component: ProductionPlannerComponent,
      },
    ]),
    SharedModule,
  ],
  entryComponents: [ProductionDeleteComponent],
})
export class ProductionModule {}
