package com.procure.web.rest;

import com.procure.domain.OrdersDetailsDashboard;
import com.procure.service.OrdersDetailsDashboardService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/ordersDetailsDashboard")
public class OrdersDetailsDashboardResource {

    private final OrdersDetailsDashboardService ordersDetailsDashboardService;

    public OrdersDetailsDashboardResource(OrdersDetailsDashboardService ordersDetailsDashboardService) {
        this.ordersDetailsDashboardService = ordersDetailsDashboardService;
    }

    @GetMapping("/getRangeDateOrders")
    public List<Map<String, String>> getRangeDateOrders(
        @RequestParam("startDate") LocalDate startDate,
        @RequestParam("endDate") LocalDate endDate
    ) {
        return ordersDetailsDashboardService.getOrdersDetailsInRange(startDate, endDate);
    }

    @GetMapping("/getPercentageForRange")
    public List<Map<String, String>> getPercentageForRange(
        @RequestParam("startDate") LocalDate startDate,
        @RequestParam("endDate") LocalDate endDate
    ) {
        return ordersDetailsDashboardService.getPercentageForRange(startDate, endDate);
    }

    @GetMapping("/getOrdersDetailsInBetween")
    public OrdersDetailsDashboard getOrdersDetailsInBetween(
        @RequestParam("startDate") LocalDate startDate,
        @RequestParam("endDate") LocalDate endDate
    ) {
        return ordersDetailsDashboardService.getOrdersDetailsInBetweem(startDate, endDate);
    }
}
