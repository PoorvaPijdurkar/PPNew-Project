/* eslint-disable @typescript-eslint/explicit-function-return-type */
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { LeadSourceService } from '../service/lead-source.service';

@Component({
  selector: 'jhi-lead-source-add',
  templateUrl: './lead-source-add.component.html',
  styleUrls: ['./lead-source-add.component.scss'],
})
export class LeadSourceAddComponent implements OnInit {
  selected: any;
  screenMode = 'add';
  isSaving = false;
  rowData: any = {};
  editForm = new FormGroup({
    id: new FormControl(null),
    sourceType: new FormControl(null, { validators: [Validators.required, Validators.maxLength(25)] }),
    description: new FormControl(null, { validators: [Validators.required] }),
    status: new FormControl(false),
  });

  constructor(private router: Router, private route: ActivatedRoute, private leadSourceService: LeadSourceService) {
    const extraParam: any = this.router.getCurrentNavigation()?.extras;
    if (![undefined, null].includes(extraParam)) {
      const stateData = extraParam['state'];
      this.screenMode = stateData['mode'];
      this.rowData = stateData['rowData'];
    }
    if (['edit', 'view'].includes(this.screenMode)) {
      Object.keys(this.rowData).forEach(key => {
        if (this.editForm.get(key)) {
          this.editForm.get(key)?.setValue(this.rowData[key]);
        }
      });
    }
    if (['view'].includes(this.screenMode)) {
      this.editForm.disable();
    }
  }

  isAddMode = () => this.screenMode === 'add';
  isEditMode = () => this.screenMode === 'edit';
  isViewMode = () => this.screenMode === 'view';

  save(): void {
    this.isSaving = true;
    const leads = this.editForm.getRawValue();
    if (leads.id !== null) {
      this.leadSourceService.update(leads).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      this.leadSourceService.create(leads).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    }
  }

  previousState(): void {
    this.router.navigate(['lead-source']);
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
