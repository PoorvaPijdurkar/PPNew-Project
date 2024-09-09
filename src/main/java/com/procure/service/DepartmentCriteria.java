package com.procure.service;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@Setter
@Getter
public class DepartmentCriteria implements Serializable, Criteria {

    public static final long serialVersionUID = 1L;
    private LongFilter departmentId;
    private StringFilter departmentCode;
    private StringFilter departmentName;

    public DepartmentCriteria() {}

    public DepartmentCriteria(DepartmentCriteria other) {
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.departmentName = other.departmentName == null ? null : other.departmentName.copy();
        this.departmentCode = other.departmentCode == null ? null : other.departmentCode.copy();
    }

    @Override
    public Criteria copy() {
        return new DepartmentCriteria(this);
    }
}
