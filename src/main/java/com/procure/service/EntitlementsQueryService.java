package com.procure.service;

import com.procure.domain.Entitlements;
import com.procure.domain.Entitlements_;
import com.procure.repository.EntitlementsRepository;
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
public class EntitlementsQueryService extends QueryService<Entitlements> {

    private final EntitlementsRepository entitlementsRepository;

    public EntitlementsQueryService(EntitlementsRepository entitlementsRepository) {
        this.entitlementsRepository = entitlementsRepository;
    }

    public List<Entitlements> findByCriteria(EntitlementsCriteria criteria) {
        log.info("find by criteria", criteria);
        final Specification<Entitlements> specification = createSpecification(criteria);
        return entitlementsRepository.findAll(specification);
    }

    public Page<Entitlements> findByCriteria(EntitlementsCriteria criteria, Pageable pageable) {
        log.info("find by criteria called", criteria);
        final Specification<Entitlements> specification = createSpecification(criteria);
        return entitlementsRepository.findAll(specification, pageable);
    }

    public Long countByCriteria(EntitlementsCriteria criteria) {
        final Specification<Entitlements> specification = createSpecification(criteria);
        return entitlementsRepository.count(specification);
    }

    private Specification<Entitlements> createSpecification(EntitlementsCriteria criteria) {
        log.info("Create specification is called");
        Specification<Entitlements> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Entitlements_.Id));
            }

            if (criteria.getRoleId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoleId(), Entitlements_.roleId));
            }

            if (criteria.getResourceId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResourceId(), Entitlements_.resourceId));
            }

            if (criteria.getResourceName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResourceName(), Entitlements_.resourceName));
            }

            if (criteria.getActionId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActionId(), Entitlements_.actionId));
            }
            if (criteria.getActionName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActionName(), Entitlements_.actionName));
            }
        }
        return specification;
    }
}
