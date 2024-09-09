package com.procure.dto;

public class BomSkusDto {

    private Long id;
    private long bomId;
    private long skuId;
    private double quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getBomId() {
        return bomId;
    }

    public void setBomId(long bomId) {
        this.bomId = bomId;
    }

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
