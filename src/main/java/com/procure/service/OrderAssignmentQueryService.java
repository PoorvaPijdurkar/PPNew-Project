package com.procure.service;

import com.procure.domain.OrderAssignment;
import com.procure.domain.OrderAssignment_;
import com.procure.domain.User_;
import com.procure.repository.OrderAssignmentRepository;
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
public class OrderAssignmentQueryService extends QueryService<OrderAssignment> {

    private final OrderAssignmentRepository orderAssignmentRepository;

    public OrderAssignmentQueryService(OrderAssignmentRepository orderAssignmentRepository) {
        this.orderAssignmentRepository = orderAssignmentRepository;
    }

    public List<OrderAssignment> findByCriteria(OrderAssignmentCriteria criteria) {
        log.info("find by criteria", criteria);
        final Specification<OrderAssignment> specification = createSpecification(criteria);
        return orderAssignmentRepository.findAll(specification);
    }

    public Page<OrderAssignment> findByCriteria(OrderAssignmentCriteria criteria, Pageable pageable) {
        log.info("find by criteria called", criteria);
        final Specification<OrderAssignment> specification = createSpecification(criteria);
        return orderAssignmentRepository.findAll(specification, pageable);
    }

    public Long countByCriteria(OrderAssignmentCriteria criteria) {
        final Specification<OrderAssignment> specification = createSpecification(criteria);
        return orderAssignmentRepository.count(specification);
    }

    private Specification<OrderAssignment> createSpecification(OrderAssignmentCriteria criteria) {
        log.info("Create specification is called");
        Specification<OrderAssignment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrderAssignment_.id));
            }
            if (criteria.getOrderNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrderNumber(), OrderAssignment_.orderNumber));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserId(), OrderAssignment_.user, User_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), OrderAssignment_.status));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), OrderAssignment_.updatedBy));
            }
            if (criteria.getAssignedQuantity() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getAssignedQuantity(), OrderAssignment_.assignedQuantity));
            }
            if (criteria.getBidRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBidRate(), OrderAssignment_.bidRate));
            }

            if (criteria.getPurchasePrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPurchasePrice(), OrderAssignment_.purchasePrice));
            }

            if (criteria.getPurchaseQuantity() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPurchaseQuantity(), OrderAssignment_.purchaseQuantity));
            }

            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), OrderAssignment_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildSpecification(criteria.getCreatedDate(), OrderAssignment_.createdDate));
            }

            if (criteria.getAssignedBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssignedBy(), OrderAssignment_.assignedBy));
            }
        }
        return specification;
    }
}
