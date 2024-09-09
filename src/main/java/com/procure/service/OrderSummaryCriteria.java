package com.procure.service;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.StringFilter;

@Setter
@Getter
public class OrderSummaryCriteria implements Serializable, Criteria {

    public static final long serialVersionUID = 1L;
    private IntegerFilter id;
    private LocalDateFilter effectiveDate;
    private StringFilter orderStatus;
    private StringFilter OrderNumber;
    private StringFilter productType;
    private StringFilter productName;

    public OrderSummaryCriteria() {}

    public OrderSummaryCriteria(OrderSummaryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.effectiveDate = other.effectiveDate == null ? null : other.effectiveDate.copy();
        this.orderStatus = other.orderStatus == null ? null : other.orderStatus.copy();
        this.OrderNumber = other.OrderNumber == null ? null : other.OrderNumber.copy();
        this.productType = other.productType == null ? null : other.productType.copy();
        this.productName = other.productName == null ? null : other.productName.copy();
    }

    @Override
    public Criteria copy() {
        return new OrderSummaryCriteria(this);
    }
}
