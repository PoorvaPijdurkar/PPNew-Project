import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SkuListComponent } from './sku-list/sku-list.component';
import { SkuAddUpdateComponent } from './sku-add-update/sku-add-update.component';
import { SkuDeleteDialogComponent } from './sku-delete/sku-delete.dialog.component';
import { RouterModule } from '@angular/router';
import { AgGridModule } from 'ag-grid-angular';
import { SharedModule } from '../shared/shared.module';
import { ActionRendererComponent } from './sku-list/action-renderer.component';

@NgModule({
  declarations: [SkuListComponent, SkuAddUpdateComponent, SkuDeleteDialogComponent, ActionRendererComponent],
  imports: [
    CommonModule,
    SharedModule,
    AgGridModule.withComponents([ActionRendererComponent]),
    RouterModule.forChild([
      {
        path: '',
        component: SkuListComponent,
      },
      {
        path: 'add',
        component: SkuAddUpdateComponent,
      },
      {
        path: 'edit',
        component: SkuAddUpdateComponent,
      },
      {
        path: 'view',
        component: SkuAddUpdateComponent,
      },
    ]),
    SharedModule,
  ],
  entryComponents: [SkuDeleteDialogComponent],
})
export class SkuModule {}
