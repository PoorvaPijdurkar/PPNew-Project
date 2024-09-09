package com.procure.service;

import com.procure.domain.OrderSummary;
import com.procure.domain.OrderSummary_;
import com.procure.repository.OrderSummaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

@Service
@Transactional(readOnly = true)
@Slf4j
public class OrdersSummaryQueryService extends QueryService<OrderSummary> {

    private final OrderSummaryRepository orderSummaryRepository;

    public OrdersSummaryQueryService(OrderSummaryRepository orderSummaryRepository) {
        this.orderSummaryRepository = orderSummaryRepository;
    }

    public Page<OrderSummary> findByCriteria(OrderSummaryCriteria orderSummaryCriteria, Pageable pageable) {
        log.info("find by criteria called", orderSummaryCriteria);
        final Specification<OrderSummary> specification = createSpecification(orderSummaryCriteria);
        return orderSummaryRepository.findAll(specification, pageable);
    }

    private Specification<OrderSummary> createSpecification(OrderSummaryCriteria orderSummaryCriteria) {
        log.info("Create specification is called");
        Specification<OrderSummary> specification = Specification.where(null);
        if (orderSummaryCriteria != null) {
            if (orderSummaryCriteria.getEffectiveDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(orderSummaryCriteria.getEffectiveDate(), OrderSummary_.effectiveDate));
            }

            if (orderSummaryCriteria.getOrderNumber() != null) {
                specification =
                    specification.and(buildStringSpecification(orderSummaryCriteria.getOrderNumber(), OrderSummary_.OrderNumber));
            }

            if (orderSummaryCriteria.getOrderStatus() != null) {
                specification =
                    specification.and(buildStringSpecification(orderSummaryCriteria.getOrderStatus(), OrderSummary_.orderStatus));
            }

            if (orderSummaryCriteria.getProductType() != null) {
                specification =
                    specification.and(buildStringSpecification(orderSummaryCriteria.getProductType(), OrderSummary_.productType));
            }

            if (orderSummaryCriteria.getProductName() != null) {
                specification =
                    specification.and(buildStringSpecification(orderSummaryCriteria.getProductName(), OrderSummary_.productName));
            }
        }
        return specification;
    }
}
