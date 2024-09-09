package com.procure.service;

import com.procure.domain.Orders;
import com.procure.domain.OrdersDetailsDashboard;
import com.procure.repository.OrdersRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrdersDetailsDashboardService {

    private final OrdersRepository ordersRepository;

    public OrdersDetailsDashboardService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public List<Map<String, String>> getOrdersDetailsInRange(LocalDate startDate, LocalDate endDate) {
        return ordersRepository.getOrdersDetailsInRange(startDate, endDate);
    }

    public List<Map<String, String>> getPercentageForRange(LocalDate startDate, LocalDate endDate) {
        return ordersRepository.getPercentageForRange(startDate, endDate);
    }

    public OrdersDetailsDashboard getOrdersDetailsInBetweem(LocalDate startDate, LocalDate endDate) {
        List<Orders> ordersList = ordersRepository.getOrdersDetailsInBetween(startDate, endDate);

        OrdersDetailsDashboard ordersDetailsDashboard1 = getOrdersDetailsDashboard(ordersList);
        return ordersDetailsDashboard1;
    }

    private OrdersDetailsDashboard getOrdersDetailsDashboard(List<Orders> ordersList) {
        Map<String, Long> statusCounts = ordersList.stream().collect(Collectors.groupingBy(Orders::getStatus, Collectors.counting()));
        OrdersDetailsDashboard ordersDetailsDashboard = new OrdersDetailsDashboard();
        Integer approvedCount = statusCounts.getOrDefault("APPROVED", 0L).intValue();
        Integer pendingCount = statusCounts.getOrDefault("WAITING FOR APPROVAL", 0L).intValue();
        Integer rejectedCount = statusCounts.getOrDefault("REJECTED", 0L).intValue();

        ordersDetailsDashboard.setOrdersCount(ordersList.size());

        ordersDetailsDashboard.setOrdersCompleted(approvedCount);
        ordersDetailsDashboard.setOrdersPending(pendingCount);
        ordersDetailsDashboard.setOrdersCancelled(rejectedCount);

        return ordersDetailsDashboard;
    }
}
