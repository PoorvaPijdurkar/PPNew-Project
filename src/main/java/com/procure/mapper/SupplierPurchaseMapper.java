package com.procure.mapper;

import com.procure.domain.SupplierPurchaseOrder;
import com.procure.dto.SupplierPurchaseOrderDto;
import org.mapstruct.Mapper;

@Mapper
public interface SupplierPurchaseMapper {
    SupplierPurchaseOrderDto sourceToDestination(SupplierPurchaseOrder supplierPurchaseOrder);

    SupplierPurchaseOrder destinationToSource(SupplierPurchaseOrderDto supplierPurchaseOrderDto);
}
