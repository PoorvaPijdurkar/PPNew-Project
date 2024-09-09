package com.procure.repository;

import com.procure.domain.ProducerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerDetailsRepository extends JpaRepository<ProducerDetails, Long>, JpaSpecificationExecutor<ProducerDetails> {}
