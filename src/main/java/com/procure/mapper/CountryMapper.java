package com.procure.mapper;

import com.procure.domain.Country;
import com.procure.dto.CountryDto;
import org.mapstruct.Mapper;

@Mapper
public interface CountryMapper {
    CountryDto sourceToDestination(Country country);

    Country destinationToSource(CountryDto countryDto);
}
