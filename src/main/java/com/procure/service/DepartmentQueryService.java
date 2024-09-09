package com.procure.service;

import com.procure.domain.Department;
import com.procure.domain.Department_;
import com.procure.repository.DepartmentRepository;
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
public class DepartmentQueryService extends QueryService<Department> {

    private final DepartmentRepository departmentRepository;

    public DepartmentQueryService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> findByCriteria(DepartmentCriteria criteria) {
        log.info("find by criteria", criteria);
        final Specification<Department> specification = createSpecification(criteria);
        return departmentRepository.findAll(specification);
    }

    public Page<Department> findByCriteria(DepartmentCriteria criteria, Pageable pageable) {
        log.info("find by criteria called", criteria);
        final Specification<Department> specification = createSpecification(criteria);
        return departmentRepository.findAll(specification, pageable);
    }

    public Long countByCriteria(DepartmentCriteria criteria) {
        final Specification<Department> specification = createSpecification(criteria);
        return departmentRepository.count(specification);
    }

    private Specification<Department> createSpecification(DepartmentCriteria criteria) {
        log.info("Create specification is called");
        Specification<Department> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getDepartmentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDepartmentId(), Department_.departmentId));
            }

            if (criteria.getDepartmentName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepartmentName(), Department_.departmentName));
            }

            if (criteria.getDepartmentCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepartmentCode(), Department_.departmentCode));
            }
        }
        return specification;
    }
}
