package com.procure.service;

import com.procure.domain.Bom;
import com.procure.domain.Bom_;
import com.procure.repository.BomRepository;
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
public class BomCriteriaQueryService extends QueryService<Bom> {

    private final BomRepository bomRepository;

    public BomCriteriaQueryService(BomRepository bomRepository) {
        this.bomRepository = bomRepository;
    }

    public List<Bom> findByCriteria(BomCriteria bomCriteria) {
        final Specification<Bom> specification = createSpecification(bomCriteria);
        return bomRepository.findAll(specification);
    }

    public Page<Bom> findByCriteria(BomCriteria bomCriteria, Pageable pageable) {
        final Specification<Bom> specification = createSpecification(bomCriteria);
        return bomRepository.findAll(specification, pageable);
    }

    private Specification<Bom> createSpecification(BomCriteria bomCriteria) {
        log.info("Create specification is called");
        Specification<Bom> specification = Specification.where(null);
        if (bomCriteria != null) {
            if (bomCriteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(bomCriteria.getId(), Bom_.id));
            }

            if (bomCriteria.getProductName() != null) {
                specification = specification.and(buildStringSpecification(bomCriteria.getProductName(), Bom_.productName));
            }
            if (bomCriteria.getBomLevel() != null) {
                specification = specification.and(buildRangeSpecification(bomCriteria.getBomLevel(), Bom_.bomLevel));
            }

            if (bomCriteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(bomCriteria.getQuantity(), Bom_.quantity));
            }

            if (bomCriteria.getUnitOfMeasure() != null) {
                specification = specification.and(buildStringSpecification(bomCriteria.getUnitOfMeasure(), Bom_.unitOfMeasure));
            }

            if (bomCriteria.getProductCode() != null) {
                specification = specification.and(buildStringSpecification(bomCriteria.getProductCode(), Bom_.productCode));
            }
            if (bomCriteria.getProductType() != null) {
                specification = specification.and(buildStringSpecification(bomCriteria.getProductType(), Bom_.productType));
            }
        }
        return specification;
    }
}
