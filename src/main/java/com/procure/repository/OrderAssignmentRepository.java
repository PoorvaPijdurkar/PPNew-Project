package com.procure.repository;

import com.procure.domain.OrderAssignment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderAssignmentRepository extends JpaRepository<OrderAssignment, Long>, JpaSpecificationExecutor<OrderAssignment> {
    @Modifying
    @Query(value = "delete from OrderAssignment order1 where order1.orderNumber = :orderNumber")
    void deleteRecordByOrderNumber(@Param("orderNumber") String orderNumber);

    @Query(value = "select * from procure.order_assignment where ORDER_NUMBER=:orderNumber AND FK_USER_ID=:fk_userId", nativeQuery = true)
    List<OrderAssignment> findByOrderNumberUserId(String orderNumber, Long fk_userId);
}
