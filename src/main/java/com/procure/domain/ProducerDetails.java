package com.procure.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "producer_details")
@Data
@JsonIgnoreProperties(value = { "id" }, allowGetters = true)
public class ProducerDetails {

    @Id
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "producer_type")
    private String producerType;

    @NotNull
    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "taluka")
    private String taluka;

    @NotNull
    @Column(name = "State")
    private String state;

    @NotNull
    @Column(name = "country")
    private String country;

    @NotNull
    @Column(name = "land_Holding")
    private double landHolding;

    @NotNull
    @Column(name = "quantity")
    private int quantity;

    @NotNull
    @Column(name = "mobile_number")
    private String mobileNumber;

    @NotNull
    @Column(name = "FIELD_OF_PRODUCER_Name")
    private String fieldOfProducerName;

    @NotNull
    @Column(name = "created_by")
    private String createdBy;

    @NotNull
    @Column(name = "updated_by")
    private String updatedBy;

    @NotNull
    @Column(name = "Fk_sku_id")
    private long fkSkuId;
}
