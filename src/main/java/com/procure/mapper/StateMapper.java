package com.procure.mapper;

import com.procure.domain.State;
import com.procure.dto.StateDto;
import org.mapstruct.Mapper;

@Mapper
public interface StateMapper {
    StateDto sourceToDestination(State state);

    State destinationToSource(StateDto stateDto);
}
