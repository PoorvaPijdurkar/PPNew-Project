import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ReactiveFormsModule } from '@angular/forms';
import { AgGridModule } from 'ag-grid-angular';
import { SharedModule } from '../shared/shared.module';
import { ActionRendererComponent } from './lead-source-list/action-renderer.component';
import { LeadSourceListComponent } from './lead-source-list/lead-source-list.component';
import { LeadSourceDeleteComponent } from './lead-source-delete/lead-source-delete.component';
import { LeadSourceAddComponent } from './lead-source-add/lead-source-add.component';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';

@NgModule({
  declarations: [ActionRendererComponent, LeadSourceListComponent, LeadSourceDeleteComponent, LeadSourceAddComponent],
  imports: [
    CommonModule,
    MatInputModule,
    FormsModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatSlideToggleModule,
    AgGridModule.withComponents([ActionRendererComponent]),
    RouterModule.forChild([
      {
        path: '',
        component: LeadSourceListComponent,
      },
      {
        path: 'add',
        component: LeadSourceAddComponent,
      },
      {
        path: 'edit',
        component: LeadSourceAddComponent,
      },
      {
        path: 'view',
        component: LeadSourceAddComponent,
      },
    ]),
    SharedModule,
  ],
  entryComponents: [LeadSourceDeleteComponent],
})
export class LeadSourceModule {}
