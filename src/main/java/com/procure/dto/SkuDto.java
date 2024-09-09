package com.procure.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkuDto {

    private Long Id;
    private String productName;
    private String productCode;
    private String productType;
    private String unitOfMeasure;
    private String message;
    private double price;
    private double quantity;
}
