import { Component, OnInit } from '@angular/core';
import { SalesOrderService } from '../service/sales-order.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-sales-order-delete',
  templateUrl: './sales-order-delete.component.html',
  styleUrls: ['./sales-order-delete.component.scss'],
})
export class SalesOrderDeleteComponent implements OnInit {
  salesOrder?: any;

  constructor(private salesOrderService: SalesOrderService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(salesId: number): void {
    this.salesOrderService.delete(salesId).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }

  ngOnInit(): void {}
}
