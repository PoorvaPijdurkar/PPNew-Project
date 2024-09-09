package com.procure.service;

import com.procure.domain.ProducerDetails;
import com.procure.domain.ProducerDetails_;
import com.procure.repository.ProducerDetailsRepository;
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
public class ProducerDetailsQueryService extends QueryService<ProducerDetails> {

    private final ProducerDetailsRepository producerDetailsRepository;

    public ProducerDetailsQueryService(ProducerDetailsRepository producerDetailsRepository) {
        this.producerDetailsRepository = producerDetailsRepository;
    }

    public List<ProducerDetails> findByCriteria(ProducerDetailsCriteria criteria) {
        final Specification<ProducerDetails> specification = createSpecification(criteria);
        return producerDetailsRepository.findAll(specification);
    }

    public Page<ProducerDetails> findByCriteria(ProducerDetailsCriteria criteria, Pageable pageable) {
        final Specification<ProducerDetails> specification = createSpecification(criteria);
        return producerDetailsRepository.findAll(specification, pageable);
    }

    public Long countByCriteria(ProducerDetailsCriteria criteria) {
        final Specification<ProducerDetails> specification = createSpecification(criteria);
        return producerDetailsRepository.count(specification);
    }

    private Specification<ProducerDetails> createSpecification(ProducerDetailsCriteria criteria) {
        Specification<ProducerDetails> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProducerDetails_.id));
            }
            if (criteria.getProducerType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProducerType(), ProducerDetails_.producerType));
            }

            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), ProducerDetails_.address));
            }
            if (criteria.getTaluka() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaluka(), ProducerDetails_.taluka));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getState(), ProducerDetails_.state));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), ProducerDetails_.country));
            }
            if (criteria.getLandHolding() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLandHolding(), ProducerDetails_.landHolding));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), ProducerDetails_.quantity));
            }
            if (criteria.getFieldOfProducerName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getFieldOfProducerName(), ProducerDetails_.fieldOfProducerName));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ProducerDetails_.createdBy));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), ProducerDetails_.updatedBy));
            }
            if (criteria.getFkSkuId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFkSkuId(), ProducerDetails_.fkSkuId));
            }

            if (criteria.getMobileNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobileNumber(), ProducerDetails_.mobileNumber));
            }
        }

        return specification;
    }
}
