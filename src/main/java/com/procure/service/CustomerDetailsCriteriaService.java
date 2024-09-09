package com.procure.service;

import com.procure.domain.CustomerDetails;
import com.procure.domain.CustomerDetails_;
import com.procure.repository.CustomerDetailsRepository;
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
public class CustomerDetailsCriteriaService extends QueryService<CustomerDetails> {

    private final CustomerDetailsRepository customerDetailsRepository;

    public CustomerDetailsCriteriaService(CustomerDetailsRepository customerDetailsRepository) {
        this.customerDetailsRepository = customerDetailsRepository;
    }

    public List<CustomerDetails> findByCriteria(CustomerDetailsCriteria customerDetailsCriteria) {
        final Specification<CustomerDetails> specification = createSpecification(customerDetailsCriteria);
        return customerDetailsRepository.findAll(specification);
    }

    public Page<CustomerDetails> findByCriteria(CustomerDetailsCriteria customerDetailsCriteria, Pageable pageable) {
        final Specification<CustomerDetails> specification = createSpecification(customerDetailsCriteria);
        return customerDetailsRepository.findAll(specification, pageable);
    }

    private Specification<CustomerDetails> createSpecification(CustomerDetailsCriteria customerDetailsCriteria) {
        log.info("Create specification is called");
        Specification<CustomerDetails> specification = Specification.where(null);

        if (customerDetailsCriteria.getId() != null) {
            specification = specification.and(buildRangeSpecification(customerDetailsCriteria.getId(), CustomerDetails_.id));
        }

        if (customerDetailsCriteria.getFirstName() != null) {
            specification = specification.and(buildStringSpecification(customerDetailsCriteria.getFirstName(), CustomerDetails_.firstName));
        }

        if (customerDetailsCriteria.getLastName() != null) {
            specification = specification.and(buildStringSpecification(customerDetailsCriteria.getLastName(), CustomerDetails_.lastName));
        }

        if (customerDetailsCriteria.getCompanyName() != null) {
            specification =
                specification.and(buildStringSpecification(customerDetailsCriteria.getCompanyName(), CustomerDetails_.companyName));
        }
        if (customerDetailsCriteria.getAddress() != null) {
            specification = specification.and(buildStringSpecification(customerDetailsCriteria.getAddress(), CustomerDetails_.address));
        }
        if (customerDetailsCriteria.getAddressLine1() != null) {
            specification =
                specification.and(buildStringSpecification(customerDetailsCriteria.getAddressLine1(), CustomerDetails_.addressLine1));
        }
        if (customerDetailsCriteria.getAddressLine2() != null) {
            specification =
                specification.and(buildStringSpecification(customerDetailsCriteria.getAddressLine2(), CustomerDetails_.addressLine2));
        }
        if (customerDetailsCriteria.getCity() != null) {
            specification = specification.and(buildStringSpecification(customerDetailsCriteria.getCity(), CustomerDetails_.city));
        }
        if (customerDetailsCriteria.getPin() != null) {
            specification = specification.and(buildStringSpecification(customerDetailsCriteria.getPin(), CustomerDetails_.pin));
        }
        if (customerDetailsCriteria.getState() != null) {
            specification = specification.and(buildStringSpecification(customerDetailsCriteria.getState(), CustomerDetails_.state));
        }
        if (customerDetailsCriteria.getCountry() != null) {
            specification = specification.and(buildStringSpecification(customerDetailsCriteria.getCountry(), CustomerDetails_.country));
        }
        if (customerDetailsCriteria.getEmail() != null) {
            specification = specification.and(buildStringSpecification(customerDetailsCriteria.getEmail(), CustomerDetails_.email));
        }

        if (customerDetailsCriteria.getMobileNo() != null) {
            specification = specification.and(buildStringSpecification(customerDetailsCriteria.getMobileNo(), CustomerDetails_.mobileNo));
        }

        if (customerDetailsCriteria.getGstNo() != null) {
            specification = specification.and(buildStringSpecification(customerDetailsCriteria.getGstNo(), CustomerDetails_.gstNo));
        }

        if (customerDetailsCriteria.getLeadSource() != null) {
            specification = specification.and(buildStringSpecification(customerDetailsCriteria.getGstNo(), CustomerDetails_.leadSource));
        }

        return specification;
    }
}
