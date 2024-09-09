package com.procure.mapper;

import com.procure.domain.ProducerDetails;
import com.procure.dto.ProducerDetailsDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ProducerDetailsMapper {
    ProducerDetailsDTO sourceToDestination(ProducerDetails producerDetails);
    ProducerDetails destinationToSource(ProducerDetailsDTO producerDetailsDTO);
}
