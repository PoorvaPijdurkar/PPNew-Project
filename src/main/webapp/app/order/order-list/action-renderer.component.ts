import { Component } from '@angular/core';
import { ICellRendererAngularComp } from 'ag-grid-angular';

@Component({
  selector: 'jhi-action-renderer',
  template: `
    <div class="btn-group" role="group" aria-label="Basic mixed styles example">
      <button
        type="button"
        (click)="onClick($event, 'view')"
        class="btn btn-sm"
        style="margin-right: 7px;padding: 2px 1px 2px 1px;border:none;"
      >
        <fa-icon icon="eye" style="color: #33b8f2;"></fa-icon>
      </button>

      <button
        type="button"
        *ngIf="showEditDeleteAction"
        (click)="onClick($event, 'edit')"
        class="btn btn-sm"
        style="margin-right: 7px;padding: 2px 1px 2px 1px;border:none;"
      >
        <fa-icon icon="pencil-alt" style="color: orange;"></fa-icon>
      </button>

      <button
        type="button"
        *ngIf="showEditDeleteAction"
        (click)="onClick($event, 'delete')"
        class="btn btn-sm"
        style="margin-right: 7px;padding: 2px 1px 2px 1px;border:none;"
      >
        <fa-icon icon="trash-alt" style="color:red;"></fa-icon>
      </button>

      <button
        type="button"
        *ngIf="showApproveAction"
        (click)="onClick($event, 'approve')"
        class="btn btn-sm"
        style="margin-right: 7px;padding: 2px 1px 2px 1px;border:none;"
      >
        <fa-icon icon="check" style="color:green;font-size: 18px;"></fa-icon>
      </button>

      <button
        type="button"
        *ngIf="showApproveAction"
        (click)="onClick($event, 'rejected')"
        class="btn btn-sm"
        style="margin-right: 7px;padding: 2px 1px 2px 1px;border:none;"
      >
        <fa-icon icon="times" style="color:red;font-size: 18px;"></fa-icon>
      </button>

      <button type="button" *ngIf="showAssignedAction" (click)="onClick($event, 'assign')" class="btn btn-primary btn-sm">
        <fa-icon icon="tasks" style="color:white;margin-right:7px;"></fa-icon>Assign<span></span>
      </button>
    </div>
  `,
})
export class ActionRendererComponent implements ICellRendererAngularComp {
  params: any;
  action: any;
  label: string | undefined;

  showEditDeleteAction = false;
  showApproveAction = false;
  showAssignedAction = false;

  roles: string[] = [];

  agInit(params: any): void {
    this.params = params;
    this.label = this.params.label || null;
    this.action = this.params.action || null;
    this.roles = this.params.roles;
    console.log('this.roles', this.roles);
    this.showAddDeleteAction();
    this.showApproveAndRejectAction();
    this.showAssign();
    console.log('showAddDeleteAction====>', this.showEditDeleteAction);
    console.log('showApproveAndRejectAction====>', this.showApproveAction);
  }

  refresh(params?: any): boolean {
    return true;
  }

  onClick($event: any, action: any) {
    if (this.params.onClick instanceof Function) {
      const params = {
        event: $event,
        action: action,
        rowData: this.params.node.data,
      };
      this.params.onClick(params);
    }
  }

  showAddDeleteAction() {
    // @ts-ignore
    this.roles?.forEach(role => {
      if (['ROLE_ADMIN', 'ROLE_PRODUCTION_OFFICER'].includes(role)) {
        this.showEditDeleteAction = true;
      }
    });
  }

  showApproveAndRejectAction() {
    // @ts-ignore
    this.roles?.forEach(role => {
      if (['ROLE_ADMIN', 'ROLE_PRODUCTION_MANAGER'].includes(role) && !['APPROVED', 'REJECTED'].includes(this.params.data.status)) {
        this.showApproveAction = true;
      }
    });
  }

  showAssign() {
    this.roles?.forEach(role => {
      if (['ROLE_ADMIN', 'ROLE_PROCURE_OFFICER'].includes(role) && ['APPROVED'].includes(this.params.data.status)) {
        this.showAssignedAction = true;
      }
    });
  }
}
