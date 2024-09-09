package com.procure.repository;

import com.procure.domain.SupplierPurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierPurchaseOrderRepository
    extends JpaRepository<SupplierPurchaseOrder, Long>, JpaSpecificationExecutor<SupplierPurchaseOrder> {}
