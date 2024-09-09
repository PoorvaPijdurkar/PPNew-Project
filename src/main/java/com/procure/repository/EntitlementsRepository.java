package com.procure.repository;

import com.procure.domain.Entitlements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EntitlementsRepository extends JpaRepository<Entitlements, Long>, JpaSpecificationExecutor<Entitlements> {}
