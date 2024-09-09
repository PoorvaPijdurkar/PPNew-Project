package com.procure.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.Immutable;

@Entity(name = "vw_production_report")
@Immutable
public class VmProductionReport {

    @Id
    private Long id;

    private long bomId;

    private String batchNumber;

    private String itemName;

    private String itemCode;

    private String itemUom;

    private String productName;

    private String productCode;

    private String productType;

    private String unitOfMeasure;

    private double price;

    private double quantity;

    private long batchSize;

    private double calculatedQty;

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemUom() {
        return itemUom;
    }

    public void setItemUom(String itemUom) {
        this.itemUom = itemUom;
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

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public long getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(long batchSize) {
        this.batchSize = batchSize;
    }

    public double getCalculatedQty() {
        return calculatedQty;
    }

    public void setCalculatedQty(double calculatedQty) {
        this.calculatedQty = calculatedQty;
    }

    public VmProductionReport() {}
}
