package com.procure.service;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@Getter
@Setter
public class ProducerDetailsCriteria implements Serializable, Criteria {

    public static final long serialVersionUID = 1L;
    private LongFilter id;
    private StringFilter producerType;
    private StringFilter address;
    private StringFilter taluka;
    private StringFilter state;
    private StringFilter country;
    private DoubleFilter landHolding;
    private IntegerFilter quantity;
    private StringFilter mobileNumber;
    private StringFilter fieldOfProducerName;
    private StringFilter createdBy;
    private StringFilter updatedBy;
    private LongFilter fkSkuId;

    public ProducerDetailsCriteria() {}

    public ProducerDetailsCriteria(ProducerDetailsCriteria producerDetailsCriteria) {
        this.id = producerDetailsCriteria.id == null ? null : producerDetailsCriteria.id.copy();
        this.producerType = producerDetailsCriteria.producerType == null ? null : producerDetailsCriteria.producerType.copy();
        this.address = producerDetailsCriteria.address == null ? null : producerDetailsCriteria.address.copy();
        this.taluka = producerDetailsCriteria.taluka == null ? null : producerDetailsCriteria.taluka.copy();
        this.state = producerDetailsCriteria.state == null ? null : producerDetailsCriteria.state.copy();
        this.country = producerDetailsCriteria.country == null ? null : producerDetailsCriteria.country.copy();
        this.landHolding = producerDetailsCriteria.landHolding == null ? null : producerDetailsCriteria.landHolding.copy();
        this.quantity = producerDetailsCriteria.quantity == null ? null : producerDetailsCriteria.quantity.copy();
        this.mobileNumber = producerDetailsCriteria.mobileNumber == null ? null : producerDetailsCriteria.mobileNumber.copy();
        this.fieldOfProducerName =
            producerDetailsCriteria.fieldOfProducerName == null ? null : producerDetailsCriteria.fieldOfProducerName.copy();
        this.createdBy = producerDetailsCriteria.createdBy == null ? null : producerDetailsCriteria.createdBy.copy();
        this.updatedBy = producerDetailsCriteria.updatedBy == null ? null : producerDetailsCriteria.updatedBy.copy();
        this.fkSkuId = producerDetailsCriteria.fkSkuId == null ? null : producerDetailsCriteria.fkSkuId.copy();
    }

    @Override
    public Criteria copy() {
        return new ProducerDetailsCriteria(this);
    }
}
