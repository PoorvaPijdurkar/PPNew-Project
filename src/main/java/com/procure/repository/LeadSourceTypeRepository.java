package com.procure.repository;

import com.procure.domain.LeadSourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadSourceTypeRepository extends JpaRepository<LeadSourceType, Long> {}
