import { Component, OnInit } from '@angular/core';
import { SupplierDetailsService } from '../service/supplier-details.service';
import { User } from '../../admin/user-management/user-management.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-supplier-details-delete',
  templateUrl: './supplier-details-delete.component.html',
  styleUrls: ['./supplier-details-delete.component.scss'],
})
export class SupplierDetailsDeleteComponent implements OnInit {
  supplier?: any;
  constructor(private supplierDetailsService: SupplierDetailsService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(supplierId: number): void {
    this.supplierDetailsService.delete(supplierId).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }

  ngOnInit(): void {}
}
