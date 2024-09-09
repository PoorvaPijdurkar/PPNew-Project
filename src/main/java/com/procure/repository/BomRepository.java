package com.procure.repository;

import com.procure.domain.Bom;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BomRepository extends JpaRepository<Bom, Long>, JpaSpecificationExecutor<Bom> {
    @Query(
        value = "SELECT sku.product_name as productName,bom_skus.quantity,sku.product_type as productType,sku.product_code as productCode,sku.unit_of_measure as unitOfMeasure, sku.price as price FROM bom JOIN bom_skus ON bom.id = bom_skus.bom_id JOIN sku ON bom_skus.sku_id = sku.id WHERE bom.id =:bomId",
        nativeQuery = true
    )
    List<Map<String, String>> getDetails(@Param("bomId") long bomId);
}
