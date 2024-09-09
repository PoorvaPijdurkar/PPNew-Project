package com.procure.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "supplier_purchase_order")
@Data
@JsonIgnoreProperties(value = { "Id" }, allowGetters = true)
public class SupplierPurchaseOrder {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "order_number")
    private String orderNumber;

    @OneToOne
    @JoinColumn(name = "FK_ORDER_ID")
    private OrderAssignment orderAssignment;

    @OneToOne
    @JoinColumn(name = "FK_SUPPLIER_ID")
    private SupplierDetails supplierDetails;

    @Column(name = "quantity_supplied")
    private BigDecimal quantitySupplied;

    @Column(name = "quantity_rejected")
    private BigDecimal quantityRejected;

    @Column(name = "weighted_average")
    private BigDecimal weightedAverage;

    @Column(name = "transportation_cost")
    private BigDecimal transportationCost;

    @Column(name = "supplier_rate_price")
    private BigDecimal supplierRatePrice;

    @Column(name = "quantity_received")
    private BigDecimal quantityReceived;

    @Column(name = "accepted_quantity")
    private BigDecimal acceptedQuantity;

    @Column(name = "rejected_quantity")
    private BigDecimal rejectedQuantity;

    @Column(name = "status")
    private String status;

    @Column(name = "remark")
    private String remark;
}
