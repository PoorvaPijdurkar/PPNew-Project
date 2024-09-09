package com.procure.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class OrderSummaryDto {

    private Integer Id;
    private LocalDate effectiveDate;
    private String orderStatus;
    private String OrderNumber;
    private String productType;
    private String productName;
}
