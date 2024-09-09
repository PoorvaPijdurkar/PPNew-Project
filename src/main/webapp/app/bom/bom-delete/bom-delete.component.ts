import { Component, OnInit } from '@angular/core';
import { BOMService } from '../service/bom.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-bom-delete',
  templateUrl: './bom-delete.component.html',
  styleUrls: ['./bom-delete.component.scss'],
})
export class BomDeleteComponent implements OnInit {
  bom?: any;

  constructor(private bomService: BOMService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(bomId: number): void {
    this.bomService.deleteBom(bomId).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }

  ngOnInit(): void {}
}
