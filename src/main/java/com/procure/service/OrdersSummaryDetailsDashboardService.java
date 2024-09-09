package com.procure.service;

import com.procure.repository.OrderSummaryRepository;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrdersSummaryDetailsDashboardService {

    private final OrderSummaryRepository orderSummaryRepository;

    public OrdersSummaryDetailsDashboardService(OrderSummaryRepository orderSummaryRepository) {
        this.orderSummaryRepository = orderSummaryRepository;
    }

    public List<Map<String, String>> getOrderAssignment(String orderNumber) {
        return orderSummaryRepository.getOrderAssignment(orderNumber);
    }
}
