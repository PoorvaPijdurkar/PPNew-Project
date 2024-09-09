package com.procure.dto;

import com.procure.domain.OrderAssignment;
import com.procure.domain.Orders;
import com.procure.domain.SupplierDetails;
import lombok.Data;

@Data
public class OrdersDetailsSummaryDashboardDto {

    private Orders orders;
    private OrderAssignment orderAssignment;
    private SupplierDetails supplierDetails;
}
