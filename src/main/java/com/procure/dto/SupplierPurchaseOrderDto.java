package com.procure.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierPurchaseOrderDto {

    private Long id;
    private String orderNumber;
    private OrderAssignmentDto orderAssignment;
    private SupplierDetailsDTO supplierDetails;
    private BigDecimal quantitySupplied;
    private BigDecimal quantityRejected;
    private BigDecimal weightedAverage;
    private BigDecimal transportationCost;
    private BigDecimal supplierRatePrice;
    private BigDecimal quantityReceived;
    private BigDecimal acceptedQuantity;
    private BigDecimal rejectedQuantity;
    private String status;
    private String remark;
}
