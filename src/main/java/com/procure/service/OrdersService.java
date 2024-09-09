package com.procure.service;

import com.procure.domain.Orders;
import com.procure.repository.OrderSummaryRepository;
import com.procure.repository.OrdersRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final OrderSummaryRepository orderSummaryRepository;

    private Orders orders;

    public OrdersService(OrdersRepository ordersRepository, OrderSummaryRepository orderSummaryRepository) {
        this.ordersRepository = ordersRepository;
        this.orderSummaryRepository = orderSummaryRepository;
    }

    public List<Orders> fetchRecords() {
        return this.ordersRepository.findAll();
    }

    public void deleteRecord(Long orderId) {
        this.ordersRepository.deleteById(orderId);
    }

    public Orders save(Orders orders) {
        log.debug("Request to save Orders :: {} ", orders);
        String orderNumber = this.getOrderNumber();
        orders.setOrderNumber(orderNumber);
        return ordersRepository.save(orders);
    }

    private String getOrderNumber() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmmss");
        return ft.format(dNow);
    }
}
