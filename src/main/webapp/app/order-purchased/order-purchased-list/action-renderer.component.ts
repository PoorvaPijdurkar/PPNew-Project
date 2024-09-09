import { Component } from '@angular/core';
import { ICellRendererAngularComp } from 'ag-grid-angular';
import { IAfterGuiAttachedParams, ICellRendererParams } from 'ag-grid-community';

@Component({
  selector: 'jhi-action-renderer',
  template: `
    <div class="btn-group" role="group" aria-label="Basic mixed styles example">
      <button
        type="button"
        *ngIf="isShowPurchaseAction"
        (click)="onClick($event, 'modify')"
        class="btn btn-primary btn-sm"
        style="margin-right: 5px"
      >
        <fa-icon icon="pencil-alt"></fa-icon>
        <span class="d-none d-md-inline">modify</span>
      </button>
    </div>

    <button
      type="button"
      *ngIf="showApproveAction"
      (click)="onClick($event, 'approve')"
      class="btn btn-primary btn-sm"
      style="margin-right: 5px"
    >
      <fa-icon icon="pencil-alt"></fa-icon>
      <span class="d-none d-md-inline">Approve</span>
    </button>
  `,
})
export class ActionRendererComponent implements ICellRendererAngularComp {
  isShowPurchaseAction = false;
  showApproveAction = false;

  params: any;
  action: any;
  label: string | undefined;

  roles: string[] = [];

  constructor() {}

  refresh(params: ICellRendererParams): boolean {
    return true;
  }
  afterGuiAttached?(params?: IAfterGuiAttachedParams | undefined): void {
    throw new Error('Method not implemented.');
  }

  agInit(params: any): void {
    this.params = params;
    this.label = this.params.label || null;
    this.action = this.params.action || null;
    this.roles = this.params.roles;
    console.log('this.roles', this.roles);
    this.showPurchaseAction();
    this.showApprovedAction();
    console.log('showPurchaseAction====>', this.showPurchaseAction);
    console.log('showApprovedAction====>', this.showApproveAction);
  }

  showPurchaseAction(): void {
    // @ts-ignore
    this.isShowPurchaseAction = false;
    this.roles.forEach(role => {
      if (['ROLE_ADMIN', 'ROLE_QU_FLD_OFFICER'].includes(role)) {
        this.isShowPurchaseAction = true;
      }
    });
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

  showApprovedAction(): void {
    // @ts-ignore
    this.roles.forEach(role => {
      if (['ROLE_ADMIN', 'ROLE_QUALITY_MANAGER'].includes(role) && !['APPROVED'].includes(this.params.data.status)) {
        this.showApproveAction = true;
      }
    });
  }
}
