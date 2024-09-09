package com.procure.repository;

import com.procure.domain.Production;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionRepository extends JpaRepository<Production, Long> {
    @Query(
        value = "SELECT s.product_name as productName,s.product_type as productType, s.PRODUCT_CODE as productCode, s.UNIT_OF_MEASURE as unitOfMeasure , concat(bs.quantity,'') quantity FROM bom_skus bs JOIN sku s ON bs.sku_id = s.id WHERE bs.bom_id =:bomId",
        nativeQuery = true
    )
    List<Map<String, String>> getDetails(@Param("bomId") Long bomId);
}
