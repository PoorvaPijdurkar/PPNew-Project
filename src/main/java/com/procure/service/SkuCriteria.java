package com.procure.service;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@Getter
@Setter
public class SkuCriteria implements Serializable, Criteria {

    public static final long serialVersionUID = 1L;
    private LongFilter id;
    private StringFilter productName;
    private StringFilter productType;
    private StringFilter productCode;
    private StringFilter unitOfMeasure;
    private DoubleFilter price;

    public SkuCriteria() {}

    public SkuCriteria(SkuCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.productName = other.productName == null ? null : other.productName.copy();
        this.productType = other.productType == null ? null : other.productType.copy();
        this.productCode = other.productCode == null ? null : other.productCode.copy();
        this.unitOfMeasure = other.unitOfMeasure == null ? null : other.unitOfMeasure.copy();
        this.price = other.price == null ? null : other.price.copy();
    }

    @Override
    public Criteria copy() {
        return new SkuCriteria(this);
    }
}
