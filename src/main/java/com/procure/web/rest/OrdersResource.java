package com.procure.web.rest;

import com.procure.domain.Orders;
import com.procure.dto.OrdersDto;
import com.procure.mapper.OrdersMapper;
import com.procure.response.ApiResponse;
import com.procure.service.OrdersCriteria;
import com.procure.service.OrdersQueryService;
import com.procure.service.OrdersService;
import com.procure.util.CommonUtil;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@Slf4j
public class OrdersResource {

    private final OrdersService ordersService;

    private final OrdersQueryService ordersQueryService;

    private final OrdersMapper ordersMapper;

    public OrdersResource(OrdersService ordersService, OrdersQueryService ordersQueryService, OrdersMapper ordersMapper) {
        this.ordersService = ordersService;
        this.ordersQueryService = ordersQueryService;
        this.ordersMapper = ordersMapper;
    }

    @GetMapping("order/fetchAllRecords")
    public ResponseEntity<List<OrdersDto>> fetchRecords() {
        List<Orders> ordersList = ordersService.fetchRecords();
        List<OrdersDto> ordersDTOS = ordersList.stream().map(ordersMapper::sourceToDestination).collect(Collectors.toList());
        return ResponseEntity.ok().body(ordersDTOS);
    }

    @GetMapping("order/count")
    public ResponseEntity<Long> countOrders(@ParameterObject @Valid OrdersCriteria criteria) {
        log.debug("Rest Request to count Orders by Criteria : {} ", criteria);
        return ResponseEntity.ok().body(ordersQueryService.countByCriteria(criteria));
    }

    @DeleteMapping("order/delete/{itemCode}")
    public ResponseEntity<ApiResponse> deleteOrders(@PathVariable Long itemCode) {
        log.debug("Rest Request to delete Orders by ItemCode :: {} ", itemCode);
        ordersService.deleteRecord(itemCode);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Orders details has been deleted successfully."), HttpStatus.OK);
    }

    @PutMapping("order/update")
    public ResponseEntity<ApiResponse> updateOrders(@Valid @RequestBody OrdersDto ordersDto) {
        log.debug("Rest Request to update Orders{} ", ordersDto);
        ordersService.save(ordersMapper.destinationToSource(ordersDto));
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Orders details has been updated successfully."), HttpStatus.OK);
    }

    @PostMapping("order")
    public ResponseEntity<ApiResponse> createOrders(@Valid @RequestBody OrdersDto ordersDto) {
        log.debug("Rest Request to save Orders{} ", ordersDto);
        Orders orders = ordersMapper.destinationToSource(ordersDto);

        ordersService.save(orders);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Orders details has been added successfully."), HttpStatus.OK);
    }

    @GetMapping("order/fetchAllByCriteria")
    public ResponseEntity<List<OrdersDto>> fetchAllByCriteria(@ParameterObject OrdersCriteria ordersCriteria) {
        log.debug("REST request to get a page of Orders by criteria: {}", ordersCriteria);
        List<Orders> ordersList = ordersQueryService.findByCriteria(ordersCriteria);
        List<OrdersDto> ordersDtoList = ordersList.stream().map(ordersMapper::sourceToDestination).collect(Collectors.toList());

        return ResponseEntity.ok().body(ordersDtoList);
    }

    @GetMapping("order/fetchAll")
    public ResponseEntity<List<OrdersDto>> fetchAllByCriteria(
        @ParameterObject OrdersCriteria criteria,
        @ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of Orders by criteria: {}", criteria);
        Page<Orders> page = ordersQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = CommonUtil.generatePaginationHttpHeader(page);
        List<OrdersDto> ordersDtoList = page.getContent().stream().map(ordersMapper::sourceToDestination).collect(Collectors.toList());
        return ResponseEntity.ok().headers(headers).body(ordersDtoList);
    }
}
