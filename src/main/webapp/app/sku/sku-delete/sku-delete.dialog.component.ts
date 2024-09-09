import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { SkuService } from '../service/sku.service';

@Component({
  selector: 'jhi-sku-delete',
  templateUrl: './sku-delete.dialog.component.html',
  styleUrls: ['./sku-delete.dialog.component.scss'],
})
export class SkuDeleteDialogComponent implements OnInit {
  sku?: any;

  constructor(private skuService: SkuService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(skuId: number): void {
    this.skuService.delete(skuId).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }

  ngOnInit(): void {}
}
