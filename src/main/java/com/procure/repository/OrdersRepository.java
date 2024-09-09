package com.procure.repository;

import com.procure.domain.Orders;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long>, JpaSpecificationExecutor<Orders> {
    @Query(
        value = "SELECT s.product_name AS productName,  COUNT(o.id)  AS ordersCount, SUM(CASE WHEN o.STATUS = 'WAITING FOR APPROVAL' THEN 1 ELSE 0 END) AS ordersPending, SUM(CASE WHEN o.STATUS = 'APPROVED' THEN 1 ELSE 0 END) AS ordersCompleted, SUM(CASE WHEN o.STATUS = 'REJECTED' THEN 1 ELSE 0 END) AS ordersCancelled FROM sku s JOIN orders o ON s.id = o.FK_SKU_ID where o.ORDER_DATE BETWEEN :startDate AND :endDate  GROUP BY s.id, s.PRODUCT_NAME ",
        nativeQuery = true
    )
    List<Map<String, String>> getOrdersDetailsInRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(
        value = "SELECT sku.product_Type, COUNT(*) AS recordCount, COUNT(*) * 100.0 / SUM(COUNT(*)) OVER () AS percentage FROM sku JOIN orders ON sku.ID = orders.FK_SKU_ID WHERE orders.ORDER_DATE Between :startDate AND :endDate  GROUP BY sku.PRODUCT_TYPE;",
        nativeQuery = true
    )
    List<Map<String, String>> getPercentageForRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT s FROM Orders s WHERE s.orderDate BETWEEN  :startDate  AND :endDate")
    List<Orders> getOrdersDetailsInBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
