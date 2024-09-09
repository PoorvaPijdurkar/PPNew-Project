package com.procure.repository;

import com.procure.domain.SupplierDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierDetailsRepository extends JpaRepository<SupplierDetails, Long>, JpaSpecificationExecutor<SupplierDetails> {}
