import { Component, OnInit } from '@angular/core';
import { ICellRendererAngularComp } from 'ag-grid-angular';
import { RowNode } from 'ag-grid-community';

@Component({
  selector: 'jhi-action-renderer',
  template: `
    <div class="btn-group" role="group" aria-label="Basic mixed styles example">
      <button type="button" class="btn btn-info btn-sm" style="margin-right: 5px">
        <span class="d-none d-md-inline">Action</span>
      </button>
    </div>
  `,
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
}
