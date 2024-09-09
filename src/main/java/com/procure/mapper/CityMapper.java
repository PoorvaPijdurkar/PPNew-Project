package com.procure.mapper;

import com.procure.domain.City;
import com.procure.dto.CityDto;
import org.mapstruct.Mapper;

@Mapper
public interface CityMapper {
    CityDto sourceToDestination(City city);

    City destinationToSource(CityDto cityDto);
}
