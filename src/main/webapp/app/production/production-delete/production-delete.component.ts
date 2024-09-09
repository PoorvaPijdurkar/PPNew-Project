import { Component, OnInit } from '@angular/core';
import { ProductionService } from '../service/production.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-production-delete',
  templateUrl: './production-delete.component.html',
  styleUrls: ['./production-delete.component.scss'],
})
export class ProductionDeleteComponent implements OnInit {
  production?: any;

  constructor(private productionService: ProductionService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(productionId: number): void {
    this.productionService.delete(productionId).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }

  ngOnInit(): void {}
}
