package com.procure.mapper;

import com.procure.domain.Entitlements;
import com.procure.dto.EntitlementsDto;
import org.mapstruct.Mapper;

@Mapper
public interface EntitlementsMapper {
    EntitlementsDto sourceToDestination(Entitlements entitlements);
    Entitlements destinationToSource(EntitlementsDto entitlementsDto);
}
