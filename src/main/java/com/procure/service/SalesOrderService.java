package com.procure.service;

import com.procure.domain.SalesOrder;
import com.procure.repository.SalesOrderRepository;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;

    public SalesOrderService(SalesOrderRepository salesOrderRepository) {
        this.salesOrderRepository = salesOrderRepository;
    }

    public List<SalesOrder> fetchAllSalesRecords() {
        return salesOrderRepository.findAll();
    }

    public void save(SalesOrder salesOrder) {
        String salesOrderNumber = this.getSalesOrderNumber();
        LocalDate currentDate = LocalDate.now();
        salesOrder.setCreatedDate(currentDate);
        salesOrder.setSalesOrderNumber(salesOrderNumber);
        salesOrderRepository.save(salesOrder);
    }

    public void deleteSalesOrderById(Long id) {
        salesOrderRepository.deleteById(id);
    }

    private String getSalesOrderNumber() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmmss");
        return ft.format(dNow);
    }
}
