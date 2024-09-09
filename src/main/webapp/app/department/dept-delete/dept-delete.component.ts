import { Component, OnInit } from '@angular/core';
import { DepartmentService } from '../service/dept.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-dept-delete',
  templateUrl: './dept-delete.component.html',
  styleUrls: ['./dept-delete.component.scss'],
})
export class DeptDeleteComponent implements OnInit {
  department?: any;

  constructor(private departmentService: DepartmentService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(departmentId: number): void {
    this.departmentService.delete(departmentId).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }

  ngOnInit(): void {}
}
