import { Component, OnInit } from '@angular/core';
import { LeadSourceService } from '../service/lead-source.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-lead-source-delete',
  templateUrl: './lead-source-delete.component.html',
  styleUrls: ['./lead-source-delete.component.scss'],
})
export class LeadSourceDeleteComponent implements OnInit {
  leadSource?: any;
  constructor(private leadSourceService: LeadSourceService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(leadSourceId: number): void {
    this.leadSourceService.delete(leadSourceId).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }

  ngOnInit(): void {}
}
