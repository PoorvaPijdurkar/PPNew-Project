package com.procure.mapper;

import com.procure.domain.SalesOrder;
import com.procure.dto.SalesOrderDto;
import org.mapstruct.Mapper;

@Mapper
public interface SalesOrderMapper {
    SalesOrderDto sourceToDestination(SalesOrder salesOrder);

    SalesOrder destinationToSource(SalesOrderDto salesOrderDto);
}
