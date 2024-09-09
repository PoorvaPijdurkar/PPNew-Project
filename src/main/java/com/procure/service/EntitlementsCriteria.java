package com.procure.service;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@Setter
@Getter
public class EntitlementsCriteria implements Serializable, Criteria {

    public static final long serialVersionUID = 1L;
    private LongFilter id;
    private StringFilter roleId;
    private StringFilter resourceId;
    private StringFilter resourceName;
    private StringFilter actionId;
    private StringFilter actionName;

    public EntitlementsCriteria() {}

    public EntitlementsCriteria(EntitlementsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.roleId = other.roleId == null ? null : other.roleId.copy();
        this.resourceId = other.resourceId == null ? null : other.resourceId.copy();
        this.resourceName = other.resourceName == null ? null : other.resourceName.copy();
        this.actionId = other.actionId == null ? null : other.actionId.copy();
        this.actionName = other.actionName == null ? null : other.actionName.copy();
    }

    @Override
    public Criteria copy() {
        return new EntitlementsCriteria(this);
    }
}
