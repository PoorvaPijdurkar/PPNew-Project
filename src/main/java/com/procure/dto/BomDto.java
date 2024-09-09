package com.procure.dto;

import lombok.ToString;

@ToString
public class BomDto {

    private Long id;
    private String productName;
    private long quantity;
    private String unitOfMeasure;
    private long bomLevel;
    private SkuDto skuDto;
    private String productCode;
    private String productType;

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public long getBomLevel() {
        return bomLevel;
    }

    public void setBomLevel(long bomLevel) {
        this.bomLevel = bomLevel;
    }

    public SkuDto getSkuDto() {
        return skuDto;
    }

    public void setSkuDto(SkuDto skuDto) {
        this.skuDto = skuDto;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
