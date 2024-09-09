import { Component } from '@angular/core';
import { ICellRendererAngularComp } from 'ag-grid-angular';

@Component({
  selector: 'jhi-action-renderer',
  template: `<div class="btn-group" role="group" aria-label="Basic mixed styles example">
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
      (click)="onClick($event, 'edit')"
      class="btn btn-sm"
      style="margin-right: 7px;padding: 2px 1px 2px 1px;border:none;"
    >
      <fa-icon icon="pencil-alt" style="color: orange;"></fa-icon>
    </button>

    <button type="button" (click)="onClick($event, 'delete')" class="btn btn-sm" style="padding: 2px 1px 2px 1px;border:none;">
      <fa-icon icon="trash-alt" style="color:red;"></fa-icon>
    </button>
  </div> `,
})
export class ActionRendererComponent implements ICellRendererAngularComp {
  params: any;
  action: any;
  label: string | undefined;
  roles: string[] = [];

  agInit(params: any): void {
    this.params = params;
    this.label = this.params.label || null;
    this.action = this.params.action || null;
    this.roles = this.params.roles;
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
}
