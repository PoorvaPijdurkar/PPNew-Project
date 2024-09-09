package com.procure.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProducerDetailsDTO {

    private Long id;

    private String producerType;

    private String address;

    private String taluka;

    private String state;

    private String country;

    private double landHolding;

    private int quantity;

    private String mobileNumber;

    private String fieldOfProducerName;

    private String createdBy;

    private String updatedBy;

    private long fkSkuId;
}
