package com.procure.web.rest;

import com.procure.domain.OrderAssignment;
import com.procure.domain.Orders;
import com.procure.dto.OrderAssignmentDto;
import com.procure.mapper.OrderAssignmentMapper;
import com.procure.repository.OrdersRepository;
import com.procure.response.ApiResponse;
import com.procure.security.jwt.JWTFilter;
import com.procure.security.jwt.TokenProvider;
import com.procure.service.OrderAssignmentCriteria;
import com.procure.service.OrderAssignmentQueryService;
import com.procure.service.OrderAssignmentService;
import com.procure.service.UserService;
import com.procure.util.CommonUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/api/ordersAssignment/")
@Slf4j
public class OrderAssignmentResource {

    private final TokenProvider tokenProvider;

    private final JWTFilter jwtFilter;

    private final OrderAssignmentService orderAssignmentService;

    private final UserService userService1;

    private final OrderAssignmentQueryService orderAssignmentQueryService;

    private final OrderAssignmentMapper orderAssignmentMapper;

    private final OrdersRepository ordersRepository;

    public OrderAssignmentResource(
        OrderAssignmentService orderAssignmentService,
        OrderAssignmentQueryService orderAssignmentQueryService,
        OrderAssignmentMapper orderAssignmentMapper,
        OrdersRepository ordersRepository,
        TokenProvider tokenProvider,
        JWTFilter jwtFilter,
        UserService userService1
    ) {
        this.orderAssignmentService = orderAssignmentService;
        this.orderAssignmentQueryService = orderAssignmentQueryService;
        this.orderAssignmentMapper = orderAssignmentMapper;
        this.ordersRepository = ordersRepository;
        this.tokenProvider = tokenProvider;
        this.jwtFilter = jwtFilter;
        this.userService1 = userService1;
    }

    @GetMapping("/fetchAllRecords")
    public ResponseEntity<List<OrderAssignmentDto>> fetchRecords() {
        List<OrderAssignment> orderAssignments = orderAssignmentService.fetchRecords();
        List<OrderAssignmentDto> orderAssignmentDtos = orderAssignments
            .stream()
            .map(orderAssignmentMapper::sourceToDestination)
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(orderAssignmentDtos);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countOrderAssignment(@ParameterObject @Valid OrderAssignmentCriteria criteria) {
        log.debug("Rest Request to count Orders Assignment by Criteria : {} ", criteria);
        return ResponseEntity.ok().body(orderAssignmentQueryService.countByCriteria(criteria));
    }

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<ApiResponse> deleteOrderAssignment(@PathVariable Long id) {
        log.debug("Rest Request to delete Orders by Id :: {} ", id);
        orderAssignmentService.deleteRecord(id);
        return new ResponseEntity<>(
            ApiResponse.getSuccessResponse("Order Assignment  details has been deleted successfully."),
            HttpStatus.OK
        );
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateOrderAssignment(
        @RequestHeader Map<String, String> header,
        @Valid @RequestBody OrderAssignmentDto orderAssignmentDto
    ) {
        log.debug("Rest Request to update Orders Assignment{} ", orderAssignmentDto);
        orderAssignmentDto.setUpdatedBy(tokenProvider.extractSubject(header.get("authorization").substring(7)));

        orderAssignmentService.save(orderAssignmentMapper.destinationToSource(orderAssignmentDto));
        return new ResponseEntity<>(
            ApiResponse.getSuccessResponse("Orders Assignment details has been updated successfully."),
            HttpStatus.OK
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createOrderAssignment(
        @RequestHeader Map<String, String> header,
        @Valid @RequestBody OrderAssignmentDto orderAssignmentDto,
        HttpServletRequest request
    ) {
        log.debug("Rest Request to save Orders{} ", orderAssignmentDto);
        String token = jwtFilter.resolveToken(request);
        orderAssignmentDto.setUpdatedBy(tokenProvider.extractSubject(token));
        OrderAssignment orderAssignment = orderAssignmentMapper.destinationToSource(orderAssignmentDto);
        orderAssignmentService.save(orderAssignment);
        return new ResponseEntity<>(
            ApiResponse.getSuccessResponse("Orders Assignment details has been added successfully."),
            HttpStatus.OK
        );
    }

    @GetMapping("/fetchAllByCriteria")
    public ResponseEntity<List<OrderAssignmentDto>> fetchAllByCriteria(@ParameterObject OrderAssignmentCriteria orderAssignmentCriteria) {
        log.debug("REST request to get a page of Orders Assignment by criteria: {}", orderAssignmentCriteria);
        List<OrderAssignment> orderAssignmentList = orderAssignmentQueryService.findByCriteria(orderAssignmentCriteria);
        List<OrderAssignmentDto> orderAssignmentDtos = orderAssignmentList
            .stream()
            .map(orderAssignmentMapper::sourceToDestination)
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(orderAssignmentDtos);
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<OrderAssignmentDto>> fetchAllByCriteria(
        @ParameterObject OrderAssignmentCriteria criteria,
        @ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of Orders by criteria: {}", criteria);
        Page<OrderAssignment> page = orderAssignmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = CommonUtil.generatePaginationHttpHeader(page);
        List<OrderAssignmentDto> orderAssignmentDtos = page
            .getContent()
            .stream()
            .map(orderAssignmentMapper::sourceToDestination)
            .collect(Collectors.toList());
        return ResponseEntity.ok().headers(headers).body(orderAssignmentDtos);
    }

    @DeleteMapping("/DeleteByOrderNumber/{orderNumer}")
    public ResponseEntity<ApiResponse> deleteOrderAssignment(@PathVariable String orderNumer) {
        log.debug("Rest Request to delete Orders by Id :: {} ", orderNumer);
        orderAssignmentService.deleteRecordByOrderNumber(orderNumer);
        return new ResponseEntity<>(
            ApiResponse.getSuccessResponse("Order Assignment  details has been deleted successfully."),
            HttpStatus.OK
        );
    }

    @PostMapping("/createAll")
    public ResponseEntity<ApiResponse> createAllOrderAssignment(
        @RequestHeader Map<String, String> header,
        @Valid @RequestBody List<OrderAssignmentDto> ordersAssignmentDto,
        HttpServletRequest request
    ) {
        String token = jwtFilter.resolveToken(request);
        String name = tokenProvider.extractSubject(token);
        Long id = userService1.getById(name);

        ordersAssignmentDto.forEach(dto -> dto.setUpdatedBy(tokenProvider.extractSubject(token)));
        ordersAssignmentDto.forEach(dto -> dto.setAssignedBy(id));

        List<OrderAssignment> ordersAssignment = ordersAssignmentDto
            .stream()
            .map(orderAssignmentMapper::destinationToSource)
            .collect(Collectors.toList());

        orderAssignmentService.saveAll(ordersAssignment);
        return new ResponseEntity<>(
            ApiResponse.getSuccessResponse("Orders Assignment details has been added successfully."),
            HttpStatus.OK
        );
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<ApiResponse> deleteAll(@RequestBody List<Long> listOfId) {
        List<Long> ordersIdList = new ArrayList<>();
        for (Long id : listOfId) {
            ordersIdList.add(id);
        }
        orderAssignmentService.deleteRecords(ordersIdList);
        return new ResponseEntity<>(
            ApiResponse.getSuccessResponse("Orders Assignment details has been deleted successfully."),
            HttpStatus.OK
        );
    }

    @GetMapping("/getOrdersDetails/{orderNumber}")
    public ResponseEntity<List<Orders>> getOrderDetails(@PathVariable String orderNumber) {
        List<Orders> ordersList = ordersRepository.findAll();
        List<Orders> list = new ArrayList<>();
        for (Orders orders : ordersList) {
            if (orders.getOrderNumber().equals(orderNumber)) {
                list.add(orders);
            }
        }
        return ResponseEntity.ok().body(list);
    }
}
