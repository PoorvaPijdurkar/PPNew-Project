package com.procure.service;

import com.procure.domain.SupplierDetails;
import com.procure.domain.SupplierPurchaseOrder;
import com.procure.repository.OrderAssignmentRepository;
import com.procure.repository.SupplierDetailsRepository;
import com.procure.repository.SupplierPurchaseOrderRepository;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class SupplierPurchaseOrderService {

    private final SupplierPurchaseOrderRepository supplierPurchaseOrderRepository;
    private final SupplierDetailsRepository supplierDetailsRepository;

    public SupplierPurchaseOrderService(
        SupplierPurchaseOrderRepository supplierPurchaseOrderRepository,
        SupplierDetailsRepository supplierDetailsRepository,
        OrderAssignmentRepository orderAssignmentRepository
    ) {
        this.supplierPurchaseOrderRepository = supplierPurchaseOrderRepository;
        this.supplierDetailsRepository = supplierDetailsRepository;
    }

    public SupplierPurchaseOrder save(SupplierPurchaseOrder supplierPurchaseOrder) {
        log.debug("Request to save Supplier :: {} ", supplierPurchaseOrder);
        SupplierDetails supplierDetails = supplierPurchaseOrder.getSupplierDetails();
        if (Objects.nonNull(supplierDetails) && Objects.isNull(supplierDetails.getId())) {
            supplierDetailsRepository.save(supplierDetails);
        }
        SupplierPurchaseOrder purchaseOrder = supplierPurchaseOrderRepository.save(supplierPurchaseOrder);
        return purchaseOrder;
    }

    public List<SupplierPurchaseOrder> fetchRecords() {
        return this.supplierPurchaseOrderRepository.findAll();
    }

    public void deleteRecord(Long id) {
        this.supplierPurchaseOrderRepository.deleteById(id);
    }

    public List<SupplierPurchaseOrder> saveAll(List<SupplierPurchaseOrder> supplierPurchaseOrders) {
        log.debug("Request to save Supplier Details :: {} ", supplierPurchaseOrders);
        return supplierPurchaseOrderRepository.saveAll(supplierPurchaseOrders);
    }
}
