package com.procure.mapper;

import com.procure.domain.SupplierDetails;
import com.procure.dto.SupplierDetailsDTO;
import org.mapstruct.Mapper;

@Mapper
public interface SupplierDetailsMapper {
    SupplierDetailsDTO sourceToDestination(SupplierDetails supplierDetails);

    SupplierDetails destinationToSource(SupplierDetailsDTO supplierDetailsDTO);
}
