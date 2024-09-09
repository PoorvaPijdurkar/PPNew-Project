package com.procure.service;

import java.io.Serializable;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.StringFilter;

public class SalesOrderCriteria implements Serializable, Criteria {

    public static final long serialVersionUID = 1L;
    private StringFilter salesOrderNumber;
    private StringFilter customerName;
    private StringFilter itemName;
    private LocalDateFilter deliveryDate;
    private StringFilter status;
    private LocalDateFilter createdDate;
    private LocalDateFilter updatedDate;

    public SalesOrderCriteria(SalesOrderCriteria salesOrderCriteria) {
        this.salesOrderNumber = salesOrderCriteria.salesOrderNumber == null ? null : salesOrderCriteria.salesOrderNumber.copy();
        this.customerName = salesOrderCriteria.customerName == null ? null : salesOrderCriteria.customerName.copy();
        this.itemName = salesOrderCriteria.itemName == null ? null : salesOrderCriteria.itemName.copy();
        this.deliveryDate = salesOrderCriteria.deliveryDate == null ? null : salesOrderCriteria.deliveryDate.copy();
        this.status = salesOrderCriteria.status == null ? null : salesOrderCriteria.status.copy();
        this.createdDate = salesOrderCriteria.createdDate == null ? null : salesOrderCriteria.createdDate.copy();
        this.updatedDate = salesOrderCriteria.updatedDate == null ? null : salesOrderCriteria.updatedDate.copy();
    }

    public StringFilter getSalesOrderNumber() {
        return salesOrderNumber;
    }

    public void setSalesOrderNumber(StringFilter salesOrderNumber) {
        this.salesOrderNumber = salesOrderNumber;
    }

    public StringFilter getCustomerName() {
        return customerName;
    }

    public void setCustomerName(StringFilter customerName) {
        this.customerName = customerName;
    }

    public StringFilter getItemName() {
        return itemName;
    }

    public void setItemName(StringFilter itemName) {
        this.itemName = itemName;
    }

    public LocalDateFilter getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateFilter deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public LocalDateFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateFilter createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateFilter getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateFilter updatedDate) {
        this.updatedDate = updatedDate;
    }

    public SalesOrderCriteria() {}

    @Override
    public Criteria copy() {
        return new SalesOrderCriteria(this);
    }
}
