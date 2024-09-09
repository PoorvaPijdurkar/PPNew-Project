package com.procure.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
public class SkuDetailsDashboard {

    private String productName;

    private Integer ordersInProgress;

    private Integer ordersCancelled;

    private Integer ordersCompleted;

    private Integer totalOrders;
}
