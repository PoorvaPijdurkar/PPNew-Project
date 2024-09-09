package com.procure.repository;

import com.procure.domain.VmProductionReport;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface VmProductionReportRepository extends JpaRepository<VmProductionReport, Long> {
    Optional<List<VmProductionReport>> findByBatchNumber(String batchNumber);
}
