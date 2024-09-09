package com.procure.mapper;

import com.procure.domain.CustomerDetails;
import com.procure.dto.CustomerDetailsDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerDetailsMapper {
    CustomerDetailsDto sourceToDestination(CustomerDetails customerDetails);

    CustomerDetails destinationToSource(CustomerDetailsDto customerDetailsDto);
}
