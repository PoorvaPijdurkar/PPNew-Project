package com.procure.domain;

import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "bom")
public class Bom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "PRODUCT_CODE")
    private String productCode;

    @Column(name = "PRODUCT_TYPE")
    private String productType;

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    @Column(name = "BOM_LEVEL")
    private long bomLevel;

    @Column(name = "QUANTITY")
    private long quantity;

    @Column(name = "UNIT_OF_MEASURE")
    private String unitOfMeasure;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "bom_skus",
        joinColumns = { @JoinColumn(name = "bom_id", referencedColumnName = "id", insertable = false, updatable = false) },
        inverseJoinColumns = { @JoinColumn(name = "sku_id", referencedColumnName = "id", insertable = false, updatable = false) }
    )
    private Set<Sku> skus;

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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public long getBomLevel() {
        return bomLevel;
    }

    public void setBomLevel(long bomLevel) {
        this.bomLevel = bomLevel;
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

    public Set<Sku> getSkus() {
        return skus;
    }

    public void setSkus(Set<Sku> skus) {
        this.skus = skus;
    }
}
