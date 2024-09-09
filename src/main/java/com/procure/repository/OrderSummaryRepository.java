package com.procure.repository;

import com.procure.domain.OrderSummary;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderSummaryRepository extends JpaRepository<OrderSummary, Integer>, JpaSpecificationExecutor<OrderSummary> {
    @Query(
        value = "select Id as id,fk_user_id as fkUserId, fk_order_number as orderNumber,status as status, updated_by as updatedBy,assigned_quantity as assignedQuantity,purchase_quantity as purchaseQuantity,purchase_price as purchasePrice,bid_rate as bidRate, created_by as createdBy, created_date as createdDate, assigned_by as assignedBy from order_assignment_view where fk_order_number = :orderNumber",
        nativeQuery = true
    )
    List<Map<String, String>> getOrderAssignment(@Param("orderNumber") String orderNumber);
}
