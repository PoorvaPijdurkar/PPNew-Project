package com.procure.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "supplier_details")
@Data
@JsonIgnoreProperties(value = { "Id" }, allowGetters = true)
public class SupplierDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "supplier_type")
    private String supplierType;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "address")
    private String address;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "gst_number")
    private String gstNumber;

    @Column(name = "total_land_holding")
    private Double totalLandHolding;

    @Column(name = "quantity_supplied")
    private Double quantitySupplied;

    @Column(name = "weighted_average")
    private Double weightedAverage;

    @OneToOne
    @JoinColumn(name = "FK_ORDER_ID")
    private Orders orders;
}
