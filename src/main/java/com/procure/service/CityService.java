package com.procure.service;

import com.procure.domain.City;
import com.procure.repository.CityRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> fetchAllCityRecords() {
        return cityRepository.findAll();
    }

    public void save(City city) {
        cityRepository.save(city);
    }

    public void deleteByCityId(Long id) {
        cityRepository.deleteById(id);
    }

    public List<City> fetchAllCityRecordsByStateCode(Long stateCode) {
        Optional<List<City>> listOptional = cityRepository.findByStateCode(stateCode);
        if (!listOptional.isEmpty()) {
            return listOptional.get();
        }
        return new ArrayList<>();
    }
}
