package com.procure.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
@JsonIgnoreProperties(value = { "Id" }, allowGetters = true)
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "order_number")
    @NotNull
    private String orderNumber;

    @Column(name = "quantity_required")
    private BigDecimal quantityRequired;

    @Column(name = "rate_Price")
    private BigDecimal ratePrice;

    @Column(name = "status")
    private String status;

    @Column(name = "ESTIMATED_DATE")
    private LocalDate estimatedDate;

    @Column(name = "ORDER_DATE")
    private LocalDate orderDate;

    @OneToOne
    @JoinColumn(name = "FK_SKU_ID")
    @JsonIgnore
    private Sku sku;
}
