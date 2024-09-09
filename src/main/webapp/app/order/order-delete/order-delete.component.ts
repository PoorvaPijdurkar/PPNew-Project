import { Component, OnInit } from '@angular/core';
import { SkuService } from '../../sku/service/sku.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { OrderService } from '../service/order.service';

@Component({
  selector: 'jhi-order-delete',
  templateUrl: './order-delete.component.html',
  styleUrls: ['./order-delete.component.scss'],
})
export class OrderDeleteComponent implements OnInit {
  order?: any;

  constructor(private orderService: OrderService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(order: number): void {
    this.orderService.delete(order).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }

  ngOnInit(): void {}
}
