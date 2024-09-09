import { Component, OnInit } from '@angular/core';
import { CustomerService } from '../service/customer.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-customer-delete',
  templateUrl: './customer-delete.component.html',
  styleUrls: ['./customer-delete.component.scss'],
})
export class CustomerDeleteComponent implements OnInit {
  customer?: any;
  constructor(private customerService: CustomerService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(customerId: number): void {
    this.customerService.delete(customerId).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }

  ngOnInit(): void {}
}
