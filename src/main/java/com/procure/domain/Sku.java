package com.procure.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "sku")
public class Sku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    @Column(name = "product_name")
    private String productName;

    @NotNull
    @Column(name = "product_type")
    private String productType;

    @NotNull
    @Column(name = "product_code")
    private String productCode;

    @NotNull
    @Column(name = "unit_of_measure")
    private String unitOfMeasure;

    @Column(name = "price")
    private double price;

    @Column(name = "QUANTITY")
    private double quantity;

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @ManyToMany(mappedBy = "skus")
    @JsonIgnore
    private Set<Bom> bom;

    public Sku(Long id, String productName, String productType, String productCode, String unitOfMeasure) {
        Id = id;
        this.productName = productName;
        this.productType = productType;
        this.productCode = productCode;
        this.unitOfMeasure = unitOfMeasure;
    }

    @Transient
    private String message;

    public Sku(String productName, String productType, String productCode, String unitOfMeasure, double price) {
        this.productName = productName;
        this.productType = productType;
        this.productCode = productCode;
        this.unitOfMeasure = unitOfMeasure;
        this.price = price;
    }

    public Sku() {}

    public Sku(String productName, String productType, String productCode, String unitOfMeasure) {
        this.productName = productName;
        this.productType = productType;
        this.productCode = productCode;
        this.unitOfMeasure = unitOfMeasure;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<Bom> getBom() {
        return bom;
    }

    public void setBom(Set<Bom> bom) {
        this.bom = bom;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return (
            "Sku{" +
            "Id=" +
            Id +
            ", productName='" +
            productName +
            '\'' +
            ", productType='" +
            productType +
            '\'' +
            ", productCode='" +
            productCode +
            '\'' +
            ", unitOfMeasure='" +
            unitOfMeasure +
            '\'' +
            ", price=" +
            price +
            ", quantity=" +
            quantity +
            ", bom=" +
            bom +
            ", message='" +
            message +
            '\'' +
            '}'
        );
    }
}
