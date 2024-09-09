package com.procure.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkuDetailsDashboardDto {

    private String productName;

    private Integer orderInProgress;

    private Integer orderCancelled;

    private Integer orderComplete;

    private Integer totalOrder;
}
