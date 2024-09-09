package com.procure.service;

import com.procure.domain.Sku;
import com.procure.domain.Sku_;
import com.procure.repository.SkuRepository;
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
public class SkuQueryService extends QueryService<Sku> {

    private final SkuRepository skuRepository;

    public SkuQueryService(SkuRepository skuRepository) {
        this.skuRepository = skuRepository;
    }

    public List<Sku> findByCriteria(SkuCriteria criteria) {
        final Specification<Sku> specification = createSpecification(criteria);
        return skuRepository.findAll(specification);
    }

    public Page<Sku> findByCriteria(SkuCriteria criteria, Pageable pageable) {
        final Specification<Sku> specification = createSpecification(criteria);
        return skuRepository.findAll(specification, pageable);
    }

    public Long countByCriteria(SkuCriteria criteria) {
        final Specification<Sku> specification = createSpecification(criteria);
        return skuRepository.count(specification);
    }

    private Specification<Sku> createSpecification(SkuCriteria criteria) {
        Specification<Sku> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Sku_.Id));
            }
            if (criteria.getProductName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductName(), Sku_.productName));
            }

            if (criteria.getProductType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductType(), Sku_.productType));
            }
            if (criteria.getProductCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductCode(), Sku_.productCode));
            }
            if (criteria.getUnitOfMeasure() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUnitOfMeasure(), Sku_.unitOfMeasure));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Sku_.price));
            }
        }
        return specification;
    }
}
