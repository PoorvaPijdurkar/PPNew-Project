package com.procure.service;

import com.procure.domain.SalesOrder;
import com.procure.domain.SalesOrder_;
import com.procure.repository.SalesOrderRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

@Service
@Transactional(readOnly = true)
public class SalesOrderCriteriaQueryService extends QueryService<SalesOrder> {

    private final SalesOrderRepository salesOrderRepository;

    public SalesOrderCriteriaQueryService(SalesOrderRepository salesOrderRepository) {
        this.salesOrderRepository = salesOrderRepository;
    }

    public List<SalesOrder> findByCriteria(SalesOrderCriteria salesOrderCriteria) {
        final Specification<SalesOrder> specification = createSalesOrderSpecification(salesOrderCriteria);
        return salesOrderRepository.findAll(specification);
    }

    public Page<SalesOrder> findByCriteria(SalesOrderCriteria salesOrderCriteria, Pageable pageable) {
        final Specification<SalesOrder> specification = createSalesOrderSpecification(salesOrderCriteria);
        return salesOrderRepository.findAll(specification, pageable);
    }

    private Specification<SalesOrder> createSalesOrderSpecification(SalesOrderCriteria salesOrderCriteria) {
        Specification<SalesOrder> specification = Specification.where(null);

        if (salesOrderCriteria != null) {
            if (salesOrderCriteria.getSalesOrderNumber() != null) {
                specification =
                    specification.and(buildStringSpecification(salesOrderCriteria.getSalesOrderNumber(), SalesOrder_.salesOrderNumber));
            }
            if (salesOrderCriteria.getItemName() != null) {
                specification = specification.and(buildStringSpecification(salesOrderCriteria.getItemName(), SalesOrder_.itemName));
            }
            if (salesOrderCriteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(salesOrderCriteria.getCreatedDate(), SalesOrder_.createdDate));
            }
            if (salesOrderCriteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(salesOrderCriteria.getStatus(), SalesOrder_.status));
            }
            if (salesOrderCriteria.getCustomerName() != null) {
                specification = specification.and(buildStringSpecification(salesOrderCriteria.getCustomerName(), SalesOrder_.customerName));
            }
            if (salesOrderCriteria.getDeliveryDate() != null) {
                specification = specification.and(buildRangeSpecification(salesOrderCriteria.getDeliveryDate(), SalesOrder_.deliveryDate));
            }
            if (salesOrderCriteria.getUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(salesOrderCriteria.getUpdatedDate(), SalesOrder_.updatedDate));
            }
        }
        return specification;
    }
}
