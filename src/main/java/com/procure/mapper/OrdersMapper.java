package com.procure.mapper;

import com.procure.domain.Orders;
import com.procure.dto.OrdersDto;
import org.mapstruct.Mapper;

@Mapper
public interface OrdersMapper {
    OrdersDto sourceToDestination(Orders orders);
    Orders destinationToSource(OrdersDto ordersDTO);
}
