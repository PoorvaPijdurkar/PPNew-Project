package com.procure.service;

import com.procure.domain.Orders;
import com.procure.domain.Orders_;
import com.procure.repository.OrdersRepository;
import java.util.List;
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
public class OrdersQueryService extends QueryService<Orders> {

    private final OrdersRepository ordersRepository;

    public OrdersQueryService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public List<Orders> findByCriteria(OrdersCriteria criteria) {
        log.info("find by criteria", criteria);
        final Specification<Orders> specification = createSpecification(criteria);
        return ordersRepository.findAll(specification);
    }

    public Page<Orders> findByCriteria(OrdersCriteria criteria, Pageable pageable) {
        log.info("find by criteria called", criteria);
        final Specification<Orders> specification = createSpecification(criteria);
        return ordersRepository.findAll(specification, pageable);
    }

    public Long countByCriteria(OrdersCriteria criteria) {
        // log.info("find by criteria", criteria);
        final Specification<Orders> specification = createSpecification(criteria);
        return ordersRepository.count(specification);
    }

    private Specification<Orders> createSpecification(OrdersCriteria criteria) {
        log.info("Create specification is called");
        Specification<Orders> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getOrderNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrderNumber(), Orders_.orderNumber));
            }

            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Orders_.Id));
            }

            if (criteria.getQuantityRequired() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantityRequired(), Orders_.quantityRequired));
            }

            if (criteria.getRatePrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRatePrice(), Orders_.ratePrice));
            }

            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Orders_.status));
            }

            if (criteria.getOrderDate() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderDate(), Orders_.orderDate));
            }
        }
        return specification;
    }
}
