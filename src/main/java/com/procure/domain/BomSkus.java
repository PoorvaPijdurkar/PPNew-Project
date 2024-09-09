package com.procure.domain;

import javax.persistence.*;

@Entity
@Table(name = "bom_skus")
public class BomSkus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BOM_ID")
    private long bomId;

    @Column(name = "SKU_ID")
    private long skuId;

    @Column(name = "QUANTITY")
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
