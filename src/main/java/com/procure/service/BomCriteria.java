package com.procure.service;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@Getter
@Setter
public class BomCriteria implements Serializable, Criteria {

    public static final long serialVersionUID = 1L;
    private LongFilter id;
    private StringFilter productName;
    private LongFilter quantity;
    private StringFilter unitOfMeasure;
    private StringFilter productCode;
    private LongFilter bomLevel;
    private StringFilter productType;

    public BomCriteria(BomCriteria bomCriteria) {
        this.id = bomCriteria.id == null ? null : bomCriteria.id.copy();
        this.productName = bomCriteria.productName == null ? null : bomCriteria.productName.copy();
        this.quantity = bomCriteria.quantity == null ? null : bomCriteria.quantity.copy();
        this.productCode = bomCriteria.productCode == null ? null : bomCriteria.productCode.copy();
        this.bomLevel = bomCriteria.bomLevel == null ? null : bomCriteria.bomLevel.copy();
        this.unitOfMeasure = bomCriteria.unitOfMeasure == null ? null : bomCriteria.unitOfMeasure.copy();
        this.productType = bomCriteria.productType == null ? null : bomCriteria.productType.copy();
    }

    public BomCriteria() {}

    @Override
    public Criteria copy() {
        return new BomCriteria(this);
    }
}
