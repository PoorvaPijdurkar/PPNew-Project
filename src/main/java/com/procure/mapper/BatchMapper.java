package com.procure.mapper;

import com.procure.domain.Batch;
import com.procure.dto.BatchDto;
import org.mapstruct.Mapper;

@Mapper
public interface BatchMapper {
    BatchDto sourceToDestination(Batch batch);

    Batch destinationToSource(BatchDto batchDto);
}
