package com.procure.repository;

import com.procure.domain.BomSkus;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BomSkusRepository extends JpaRepository<BomSkus, Long> {
    @Modifying
    @Transactional
    @Query(value = "delete from procure.bom_skus where bom_id =:bomId", nativeQuery = true)
    void deleteByBomId(@Param("bomId") long bomId);
}
