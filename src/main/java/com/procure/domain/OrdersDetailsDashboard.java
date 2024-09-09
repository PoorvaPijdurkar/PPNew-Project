package com.procure.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class OrdersDetailsDashboard {

    private Integer OrdersCount;
    private Integer OrdersCompleted;
    private Integer OrdersCancelled;
    private Integer OrdersPending;
}
