import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { DepartmentService } from '../service/dept.service';

@Component({
  selector: 'jhi-dept-add-update',
  templateUrl: './dept-add-update.component.html',
  styleUrls: ['./dept-add-update.component.scss'],
})
export class DeptAddUpdateComponent implements OnInit {
  selected: any;
  screenMode = 'add';
  isSaving = false;
  rowData: any = {};
  editForm = new FormGroup({
    departmentId: new FormControl(null),
    departmentName: new FormControl(null, { validators: [Validators.required, Validators.maxLength(25)] }),
    departmentCode: new FormControl(null, { validators: [Validators.required, Validators.maxLength(25)] }),
  });

  constructor(private router: Router, private departmentService: DepartmentService) {
    const extraParam: any = this.router.getCurrentNavigation()?.extras;
    if (![undefined, null].includes(extraParam)) {
      const stateData = extraParam['state'];
      this.screenMode = stateData['mode'];
      this.rowData = stateData['rowData'];
    }
    if (['edit', 'view'].includes(this.screenMode)) {
      this.editForm.get('departmentId')?.setValue(this.rowData['departmentId']);
      this.editForm.get('departmentName')?.setValue(this.rowData['departmentName']);
      this.editForm.get('departmentCode')?.setValue(this.rowData['departmentCode']);
      this.editForm.get('departmentName')?.disable();
    }
    if (['view'].includes(this.screenMode)) {
      this.editForm.get('departmentId')?.disable();
      this.editForm.get('departmentName')?.disable();
      this.editForm.get('departmentCode')?.disable();
    }
  }

  isAddMode(): boolean {
    return this.screenMode === 'add';
  }

  isEditMode(): boolean {
    return this.screenMode === 'edit';
  }
  isViewMode(): boolean {
    return this.screenMode === 'view';
  }

  save(): void {
    this.isSaving = true;
    const department: any = this.editForm.getRawValue();
    if (department.departmentId !== null) {
      this.departmentService.update(department).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      this.departmentService.create(department).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    }
  }
  previousState(): void {
    // window.history.back();
    this.router.navigate(['department']);
  }
  ngOnInit(): void {}

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }
}
