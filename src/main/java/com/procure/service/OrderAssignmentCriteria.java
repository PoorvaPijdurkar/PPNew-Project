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
public class OrderAssignmentCriteria implements Serializable, Criteria {

    private LongFilter id;
    private StringFilter orderNumber;
    private LongFilter userId;
    private StringFilter status;
    private StringFilter updatedBy;
    private BigDecimalFilter assignedQuantity;
    private BigDecimalFilter bidRate;
    private BigDecimalFilter purchasePrice;
    private BigDecimalFilter purchaseQuantity;
    private StringFilter createdBy;
    private LocalDateFilter createdDate;
    private LongFilter assignedBy;

    public OrderAssignmentCriteria() {}

    public OrderAssignmentCriteria(OrderAssignmentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.orderNumber = other.orderNumber == null ? null : other.orderNumber.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.updatedBy = other.updatedBy == null ? null : other.updatedBy.copy();
        this.assignedQuantity = other.assignedQuantity == null ? null : other.assignedQuantity.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.assignedBy = other.assignedBy == null ? null : other.assignedBy.copy();
        this.bidRate = other.bidRate == null ? null : other.bidRate.copy();
    }

    @Override
    public Criteria copy() {
        return new OrderAssignmentCriteria(this);
    }
}
