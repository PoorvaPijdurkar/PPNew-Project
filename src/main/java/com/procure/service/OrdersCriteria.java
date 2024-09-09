package com.procure.service;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@Setter
@Getter
public class OrdersCriteria implements Serializable, Criteria {

    public static final long serialVersionUID = 1L;

    private LongFilter id;
    private LongFilter fkSkuId;
    private StringFilter orderNumber;
    private BigDecimalFilter quantityRequired;
    private BigDecimalFilter ratePrice;
    private StringFilter status;
    private LocalDateFilter estimatedDate;
    private LocalDateFilter orderDate;

    public OrdersCriteria() {}

    public OrdersCriteria(OrdersCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fkSkuId = other.fkSkuId == null ? null : other.fkSkuId.copy();
        this.orderNumber = other.orderNumber == null ? null : other.orderNumber.copy();
        this.quantityRequired = other.quantityRequired == null ? null : other.quantityRequired.copy();
        this.ratePrice = other.ratePrice == null ? null : other.ratePrice.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.estimatedDate = other.estimatedDate == null ? null : other.estimatedDate.copy();
        this.orderDate = other.orderDate == null ? null : other.orderDate.copy();
    }

    @Override
    public Criteria copy() {
        return new OrdersCriteria(this);
    }
}
