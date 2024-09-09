package com.procure.mapper;

import com.procure.domain.LeadSourceType;
import com.procure.dto.LeadSourceTypeDto;
import org.mapstruct.Mapper;

@Mapper
public interface LeadSourceTypeMapper {
    LeadSourceTypeDto sourceToDestination(LeadSourceType leadSourceType);

    LeadSourceType destinationToSource(LeadSourceTypeDto leadSourceTypeDto);
}
