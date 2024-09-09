package com.procure.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.procure.domain.User;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderAssignmentDto {

    private Long id;
    private String orderNumber;
    private String status;

    @JsonIgnore
    private String updatedBy;

    private BigDecimal assignedQuantity;
    private String createdBy;

    @JsonIgnore
    private LocalDate createdDate = LocalDate.now();

    private Long assignedBy;
    private BigDecimal bidRate;
    private BigDecimal purchaseQuantity;
    private BigDecimal purchasePrice;
    private User user;
}
