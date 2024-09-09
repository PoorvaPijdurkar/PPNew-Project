package com.procure.mapper;

import com.procure.domain.Sku;
import com.procure.dto.SkuDto;
import org.mapstruct.Mapper;

@Mapper
public interface SkuMapper {
    SkuDto sourceToDestination(Sku sku);
    Sku destinationToSource(SkuDto skuDto);
}
