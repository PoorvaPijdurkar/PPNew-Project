import { Component, OnInit } from '@angular/core';
import { OrderPurchasedService } from '../service/order-purchased.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-order-purchased-approve',
  templateUrl: './order-purchased-approve.component.html',
  styleUrls: ['./order-purchased-approve.component.scss'],
})
export class OrderPurchasedApproveComponent implements OnInit {
  orderPurchase?: any;
  action?: string;

  constructor(private orderPurchasedService: OrderPurchasedService, private activeModal: NgbActiveModal) {}

  ngOnInit(): void {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(orderPurchase: any): void {
    if (this.action === 'Approve') {
      orderPurchase['status'] = 'APPROVED';

      this.orderPurchasedService.updateOrderPurchase(orderPurchase).subscribe(() => {
        this.activeModal.close(this.action);
      });
    }
  }
}
