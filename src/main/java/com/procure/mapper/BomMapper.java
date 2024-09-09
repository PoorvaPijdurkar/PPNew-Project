package com.procure.mapper;

import com.procure.domain.Bom;
import com.procure.dto.BomDto;
import org.mapstruct.Mapper;

@Mapper
public interface BomMapper {
    BomDto sourceToDestination(Bom bom);

    Bom destinationToSource(BomDto bomDto);
}
