package com.procure.web.rest;

import com.procure.domain.OrderSummary;
import com.procure.dto.OrderSummaryDto;
import com.procure.mapper.OrdersSummaryMapper;
import com.procure.service.OrderSummaryCriteria;
import com.procure.service.OrdersSummaryDetailsDashboardService;
import com.procure.service.OrdersSummaryQueryService;
import com.procure.util.CommonUtil;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
@Slf4j
public class OrdersSummaryDetailsDashboardResource {

    private final OrdersSummaryDetailsDashboardService ordersSummaryDetailsDashboardService;

    private final OrdersSummaryQueryService ordersSummaryQueryService;

    private final OrdersSummaryMapper ordersSummaryMapper;

    public OrdersSummaryDetailsDashboardResource(
        OrdersSummaryDetailsDashboardService ordersSummaryDetailsDashboardService,
        OrdersSummaryQueryService ordersSummaryQueryService,
        OrdersSummaryMapper ordersSummaryMapper
    ) {
        this.ordersSummaryDetailsDashboardService = ordersSummaryDetailsDashboardService;
        this.ordersSummaryQueryService = ordersSummaryQueryService;
        this.ordersSummaryMapper = ordersSummaryMapper;
    }

    @GetMapping("/getOrdersSummaryDetailsInRange")
    public ResponseEntity<List<OrderSummaryDto>> getOrderDetailsSummary(
        @ParameterObject OrderSummaryCriteria orderSummaryCriteria,
        @ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of Orders by criteria: {}", orderSummaryCriteria);
        Page<OrderSummary> page = ordersSummaryQueryService.findByCriteria(orderSummaryCriteria, pageable);
        HttpHeaders headers = CommonUtil.generatePaginationHttpHeader(page);
        List<OrderSummaryDto> ordersDtoList = page
            .getContent()
            .stream()
            .map(ordersSummaryMapper::sourceToDestination)
            .collect(Collectors.toList());
        return ResponseEntity.ok().headers(headers).body(ordersDtoList);
    }

    @GetMapping("/getOrderAssignmentDetails")
    public List<Map<String, String>> getOrdersDetails(@RequestParam("orderNumber") String orderNumber) {
        return ordersSummaryDetailsDashboardService.getOrderAssignment(orderNumber);
    }
}
