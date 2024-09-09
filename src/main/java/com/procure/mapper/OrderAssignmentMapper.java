package com.procure.mapper;

import com.procure.domain.OrderAssignment;
import com.procure.dto.OrderAssignmentDto;
import org.mapstruct.Mapper;

@Mapper
public interface OrderAssignmentMapper {
    OrderAssignmentDto sourceToDestination(OrderAssignment orderAssignment);
    OrderAssignment destinationToSource(OrderAssignmentDto orderAssignmentDto);
}
