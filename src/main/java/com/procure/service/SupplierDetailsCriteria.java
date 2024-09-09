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
public class SupplierDetailsCriteria implements Serializable, Criteria {

    public static final long serialVersionUID = 1L;
    private LongFilter id;
    private StringFilter orderNumber;
    private StringFilter supplierType;
    private StringFilter firstName;
    private StringFilter lastName;
    private StringFilter emailId;
    private StringFilter address;
    private StringFilter mobileNumber;
    private StringFilter gstNumber;
    private DoubleFilter totalLandHolding;
    private DoubleFilter quantitySupplied;
    private DoubleFilter weightedAverage;

    public SupplierDetailsCriteria() {}

    public SupplierDetailsCriteria(SupplierDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.orderNumber = other.orderNumber == null ? null : other.orderNumber.copy();
        this.supplierType = other.supplierType == null ? null : other.supplierType.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.emailId = other.emailId == null ? null : other.emailId.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.mobileNumber = other.mobileNumber == null ? null : other.mobileNumber.copy();
        this.gstNumber = other.gstNumber == null ? null : other.gstNumber.copy();
        this.totalLandHolding = other.totalLandHolding == null ? null : other.totalLandHolding.copy();
        this.quantitySupplied = other.quantitySupplied == null ? null : other.quantitySupplied.copy();
        this.weightedAverage = other.weightedAverage == null ? null : other.weightedAverage.copy();
    }

    @Override
    public Criteria copy() {
        return new SupplierDetailsCriteria(this);
    }
}
