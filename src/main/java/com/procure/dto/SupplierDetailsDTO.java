package com.procure.dto;

import com.procure.domain.Orders;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierDetailsDTO {

    private Long id;
    private String orderNumber;
    private String supplierType;
    private String firstName;
    private String lastName;
    private String emailId;
    private String address;
    private String mobileNumber;
    private String gstNumber;
    private Double totalLandHolding;
    private Double quantitySupplied;
    private Double weightedAverage;
    private Orders orders;
}
