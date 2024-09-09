package com.procure.repository;

import com.procure.domain.Country;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<List<Country>> findByCountryCode(String countryCode);
}
