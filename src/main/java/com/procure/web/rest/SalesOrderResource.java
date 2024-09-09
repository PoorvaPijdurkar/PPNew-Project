package com.procure.web.rest;

import com.procure.domain.SalesOrder;
import com.procure.dto.SalesOrderDto;
import com.procure.mapper.SalesOrderMapper;
import com.procure.response.ApiResponse;
import com.procure.service.SalesOrderCriteria;
import com.procure.service.SalesOrderCriteriaQueryService;
import com.procure.service.SalesOrderService;
import com.procure.util.CommonUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/salesOrder")
public class SalesOrderResource {

    private final SalesOrderService salesOrderService;
    private final SalesOrderMapper salesOrderMapper;

    private final SalesOrderCriteriaQueryService salesOrderCriteriaQueryService;

    public SalesOrderResource(
        SalesOrderService salesOrderService,
        SalesOrderMapper salesOrderMapper,
        SalesOrderCriteriaQueryService salesOrderCriteriaQueryService
    ) {
        this.salesOrderService = salesOrderService;
        this.salesOrderMapper = salesOrderMapper;
        this.salesOrderCriteriaQueryService = salesOrderCriteriaQueryService;
    }

    @GetMapping("/fetchAllSalesOrderRecords")
    public ResponseEntity<List<SalesOrder>> fetchAllSalesRecords() {
        List<SalesOrder> salesOrders = salesOrderService.fetchAllSalesRecords();
        return ResponseEntity.ok(salesOrders);
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@RequestBody SalesOrderDto salesOrderDto) {
        SalesOrder salesOrder = salesOrderMapper.destinationToSource(salesOrderDto);
        salesOrderService.save(salesOrder);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Sales Orders details has been saved successfully"), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> update(@RequestBody SalesOrderDto salesOrderDto) {
        SalesOrder salesOrder = salesOrderMapper.destinationToSource(salesOrderDto);
        salesOrderService.save(salesOrder);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Sales orders details has been updated successfully"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("id") Long id) {
        salesOrderService.deleteSalesOrderById(id);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Sales order record has been deleted successfully"), HttpStatus.OK);
    }

    @GetMapping("/fetchAllSalesOrderByCriteria")
    public ResponseEntity<List<SalesOrder>> fetchAllRecordsByCriteria(@ParameterObject SalesOrderCriteria salesOrderCriteria) {
        List<SalesOrder> salesOrderList = salesOrderCriteriaQueryService.findByCriteria(salesOrderCriteria);
        return new ResponseEntity<>(salesOrderList, HttpStatus.OK);
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<SalesOrderDto>> fetchAllByCriteria(
        @ParameterObject SalesOrderCriteria salesOrderCriteria,
        @ParameterObject Pageable pageable
    ) {
        Page<SalesOrder> page = salesOrderCriteriaQueryService.findByCriteria(salesOrderCriteria, pageable);
        HttpHeaders headers = CommonUtil.generatePaginationHttpHeader(page);
        List<SalesOrderDto> salesOrderDtoList = page
            .getContent()
            .stream()
            .map(salesOrderMapper::sourceToDestination)
            .collect(Collectors.toList());
        return ResponseEntity.ok().headers(headers).body(salesOrderDtoList);
    }
}
