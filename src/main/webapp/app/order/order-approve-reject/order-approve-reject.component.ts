import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { OrderService } from '../service/order.service';

@Component({
  selector: 'jhi-order-delete',
  templateUrl: './order-approve-reject.component.html',
  styleUrls: ['./order-approve-reject.component.scss'],
})
export class OrderApproveRejectComponent implements OnInit {
  order?: any;
  action?: string;
  constructor(private orderService: OrderService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(order: any): void {
    if (this.action === 'Approve') {
      order['status'] = 'APPROVED';
    } else {
      order['status'] = 'REJECTED';
    }
    this.orderService.update(order).subscribe(() => {
      this.activeModal.close(this.action);
    });
  }

  ngOnInit(): void {}
}
