package com.procure.repository;

import com.procure.domain.State;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long>, JpaSpecificationExecutor<State> {
    Optional<List<State>> findByCountryCode(Long countryCode);
}
