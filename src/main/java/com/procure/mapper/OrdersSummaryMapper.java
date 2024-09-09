package com.procure.mapper;

import com.procure.domain.OrderSummary;
import com.procure.dto.OrderSummaryDto;
import org.mapstruct.Mapper;

@Mapper
public interface OrdersSummaryMapper {
    OrderSummaryDto sourceToDestination(OrderSummary orderSummary);

    OrderSummary destinationToSource(OrderSummaryDto orderSummaryDto);
}
