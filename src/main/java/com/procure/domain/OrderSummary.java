package com.procure.domain;

import java.time.LocalDate;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "view_orders_summary")
@Immutable
public class OrderSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "order_number")
    private String OrderNumber;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "product_name")
    private String productName;
}
