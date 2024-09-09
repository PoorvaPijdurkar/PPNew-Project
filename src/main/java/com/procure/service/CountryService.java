package com.procure.service;

import com.procure.domain.Country;
import com.procure.repository.CountryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> fetchAllRecords() {
        return countryRepository.findAll();
    }

    public void save(Country country) {
        countryRepository.save(country);
    }

    public void deleteCountryRecordById(Long id) {
        countryRepository.deleteById(id);
    }

    public List<Country> fetchCountryRecordsByCountryCode(String countryCode) {
        Optional<List<Country>> optionalCountries = countryRepository.findByCountryCode(countryCode);
        if (!optionalCountries.isEmpty()) {
            return optionalCountries.get();
        }
        return new ArrayList<>();
    }
}
