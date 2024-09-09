// Author: Shivshankar Kadwade

import { Component } from '@angular/core';
import { ICellRendererAngularComp } from 'ag-grid-angular';

@Component({
  selector: 'jhi-action-renderer',
  template: `
    <div class="btn-group" role="group" aria-label="Basic mixed styles example">
      <button
        type="button"
        *ngIf="isShowPurchaseAction"
        (click)="onClick($event, 'purchase')"
        class="btn btn-primary btn-sm"
        style="margin-right: 5px"
      >
        <fa-icon icon="pencil-alt"></fa-icon>
        <span class="d-none d-md-inline">Purchase</span>
      </button>
    </div>
  `,
})
export class ActionRendererOrderComponent implements ICellRendererAngularComp {
  params: any;
  action: any;
  label: string | undefined;

  isShowPurchaseAction = false;
  roles: string[] = [];

  agInit(params: any): void {
    this.params = params;
    this.label = this.params.label || null;
    this.action = this.params.action || null;
    this.roles = this.params.roles;
    console.log('this.roles', this.roles);
    this.showPurchaseAction();

    console.log('showPurchaseAction====>', this.showPurchaseAction);
  }

  refresh(params?: any): boolean {
    return true;
  }

  onClick($event: any, action: any): void {
    if (this.params.onClick instanceof Function) {
      const params = {
        event: $event,
        action,
        rowData: this.params.node.data,
      };
      this.params.onClick(params);
    }
  }

  showPurchaseAction(): void {
    // @ts-ignore
    this.isShowPurchaseAction = true;
    this.roles?.forEach(role => {
      if (['ROLE_ADMIN', 'ROLE_FLD_OFFICER'].includes(role)) {
        this.isShowPurchaseAction = true;
      }
    });
  }
}
