package com.procure.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Order_Assignment")
@Data
public class OrderAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FK_ORDER_NUMBER")
    private String orderNumber;

    @Column(name = "status")
    private String status;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "assigned_quantity")
    private BigDecimal assignedQuantity;

    @Column(name = "purchase_quantity")
    private BigDecimal purchaseQuantity;

    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;

    @Column(name = "bid_rate")
    private BigDecimal bidRate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "assigned_by")
    private Long assignedBy;

    @OneToOne
    @JoinColumn(name = "FK_USER_ID")
    private User user;
}
