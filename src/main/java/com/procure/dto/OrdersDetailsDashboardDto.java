package com.procure.dto;

import lombok.Data;

@Data
public class OrdersDetailsDashboardDto {

    private Integer totalOrdersCount;

    private Integer totalOrderCompleted;

    private Integer totalOrderCancelled;

    private Integer totalOrderPending;
}
