package com.procure.service;

import com.procure.domain.OrderAssignment;
import com.procure.repository.OrderAssignmentRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class OrderAssignmentService {

    private final OrderAssignmentRepository orderAssignmentRepository;

    private OrderAssignment orderAssignment;

    public OrderAssignmentService(OrderAssignmentRepository orderAssignmentRepository) {
        this.orderAssignmentRepository = orderAssignmentRepository;
    }

    public List<OrderAssignment> fetchRecords() {
        return this.orderAssignmentRepository.findAll();
    }

    public void deleteRecord(Long orderAssignmentId) {
        this.orderAssignmentRepository.deleteById(orderAssignmentId);
    }

    public OrderAssignment save(OrderAssignment orderAssignment) {
        log.debug("Request to save OrderAssignment :: {} ", orderAssignment);
        return orderAssignmentRepository.save(orderAssignment);
    }

    public List<OrderAssignment> saveAll(List<OrderAssignment> ordersAssignments) {
        log.debug("Request to save OrderAssignment :: {} ", ordersAssignments);
        return orderAssignmentRepository.saveAll(ordersAssignments);
    }

    public void deleteRecordByOrderNumber(String orderNumber) {
        orderAssignmentRepository.deleteRecordByOrderNumber(orderNumber);
    }

    public void deleteRecords(List<Long> ids) {
        for (Long id : ids) {
            orderAssignmentRepository.deleteById(id);
        }
    }
}
