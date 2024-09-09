package com.procure.service;

import com.procure.domain.ProducerDetails;
import com.procure.domain.User;
import com.procure.repository.UserRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

@Service
@Transactional(readOnly = true)
@Slf4j
public class UserQueryService extends QueryService<ProducerDetails> {

    private final UserRepository userRepository;

    public UserQueryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findByCriteria(UserCriteria criteria) {
        final Specification<User> specification = createSpecification(criteria);
        return userRepository.findAll(specification);
    }

    private Specification<User> createSpecification(UserCriteria criteria) {
        Specification<User> specification = Specification.where(null);
        //        if (criteria != null) {
        //            if (criteria.getId() != null) {
        //                specification = specification.and(buildSpecification(criteria.getId(), User_.id));
        //            }
        //            if (criteria.getFirstName() != null) {
        //                specification = specification.and(buildStringSpecification(criteria.getFirstName(), User_.firstName));
        //            }
        //
        //            if (criteria.getLastName() != null) {
        //                specification = specification.and(buildSpecification(criteria.getLastName(), User_.lastName));
        //            }
        //            if (criteria.getDepartment() != null) {
        //                specification = specification.and(buildSpecification(criteria.getDepartment(), User_.department));
        //            }
        //            if (criteria.getEmail() != null) {
        //                specification = specification.and(buildSpecification(criteria.getEmail(), User_.email));
        //            }
        //            if (criteria.getActivated() != null) {
        //                specification = specification.and(buildSpecification(criteria.getActivated(), User_.activated));
        //            }
        //            if (criteria.getActivationKey() != null) {
        //                specification = specification.and(buildSpecification(criteria.getActivationKey(), User_.activationKey));
        //            }
        //            if (criteria.getLangKey() != null) {
        //                specification = specification.and(buildSpecification(criteria.getLangKey(), User_.langKey));
        //            }
        //            if (criteria.getImageUrl() != null) {
        //                specification = specification.and(buildSpecification(criteria.getImageUrl(), User_.imageUrl));
        //            }
        //            if (criteria.getResetKey() != null) {
        //                specification = specification.and(buildSpecification(criteria.getResetKey(), User_.resetKey));
        //            }
        //            if (criteria.getResetDate() != null) {
        //                specification = specification.and(buildSpecification(criteria.getResetDate(), User_.resetDate));
        //            }
        //        }
        return specification;
    }
}
