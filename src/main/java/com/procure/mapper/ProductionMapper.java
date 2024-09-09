package com.procure.mapper;

import com.procure.domain.Production;
import com.procure.dto.ProductionDto;
import org.mapstruct.Mapper;

@Mapper
public interface ProductionMapper {
    Production sourceToDestination(ProductionDto productionDto);

    Production destinationToSource(ProductionDto productionDto);
}
