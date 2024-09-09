package com.procure.service;

import com.procure.domain.SupplierDetails;
import com.procure.repository.SupplierDetailsRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class SupplierService {

    private final SupplierDetailsRepository supplierDetailsRepository;

    public SupplierService(SupplierDetailsRepository supplierDetailsRepository) {
        this.supplierDetailsRepository = supplierDetailsRepository;
    }

    public List<SupplierDetails> fetchRecords() {
        return this.supplierDetailsRepository.findAll();
    }

    public SupplierDetails save(SupplierDetails supplierDetails) {
        log.debug("Request to save Supplier :: {} ", supplierDetails);

        return supplierDetailsRepository.save(supplierDetails);
    }

    public void deleteRecord(Long id) {
        this.supplierDetailsRepository.deleteById(id);
    }

    public List<SupplierDetails> saveAll(List<SupplierDetails> supplierDetails) {
        log.debug("Request to save Supplier Details :: {} ", supplierDetails);
        return supplierDetailsRepository.saveAll(supplierDetails);
    }
}
