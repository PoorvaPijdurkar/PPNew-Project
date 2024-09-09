package com.procure.dto;

import com.procure.domain.Sku;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersDto {

    private Long id;
    private String orderNumber;
    private BigDecimal ratePrice;
    private BigDecimal quantityRequired;
    private String status;
    private LocalDate estimatedDate;
    private LocalDate orderDate;
    private Sku sku;
}
