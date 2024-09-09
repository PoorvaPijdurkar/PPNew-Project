package com.procure.service;

import com.procure.domain.SupplierDetails;
import com.procure.domain.SupplierDetails_;
import com.procure.repository.SupplierDetailsRepository;
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
public class SupplierDetailsQueryService extends QueryService<SupplierDetails> {

    private final SupplierDetailsRepository supplierDetailsRepository;

    public SupplierDetailsQueryService(SupplierDetailsRepository supplierDetailsRepository) {
        this.supplierDetailsRepository = supplierDetailsRepository;
    }

    public List<SupplierDetails> findByCriteria(SupplierDetailsCriteria criteria) {
        final Specification<SupplierDetails> specification = createSpecification(criteria);
        return supplierDetailsRepository.findAll(specification);
    }

    public Page<SupplierDetails> findByCriteria(SupplierDetailsCriteria criteria, Pageable pageable) {
        final Specification<SupplierDetails> specification = createSpecification(criteria);
        return supplierDetailsRepository.findAll(specification, pageable);
    }

    public Long countByCriteria(SupplierDetailsCriteria criteria) {
        final Specification<SupplierDetails> specification = createSpecification(criteria);
        return supplierDetailsRepository.count(specification);
    }

    private Specification<SupplierDetails> createSpecification(SupplierDetailsCriteria criteria) {
        Specification<SupplierDetails> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SupplierDetails_.Id));
            }

            if (criteria.getSupplierType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSupplierType(), SupplierDetails_.supplierType));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), SupplierDetails_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), SupplierDetails_.lastName));
            }
            if (criteria.getEmailId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailId(), SupplierDetails_.emailId));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), SupplierDetails_.address));
            }
            if (criteria.getMobileNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobileNumber(), SupplierDetails_.mobileNumber));
            }
            if (criteria.getGstNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGstNumber(), SupplierDetails_.gstNumber));
            }
            if (criteria.getTotalLandHolding() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalLandHolding(), SupplierDetails_.totalLandHolding));
            }
            if (criteria.getQuantitySupplied() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getQuantitySupplied(), SupplierDetails_.quantitySupplied));
            }
            if (criteria.getWeightedAverage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeightedAverage(), SupplierDetails_.weightedAverage));
            }
        }

        return specification;
    }
}
