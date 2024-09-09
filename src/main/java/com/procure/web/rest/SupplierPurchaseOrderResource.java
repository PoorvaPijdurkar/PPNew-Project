package com.procure.web.rest;

import com.procure.domain.SupplierPurchaseOrder;
import com.procure.dto.SupplierPurchaseOrderDto;
import com.procure.mapper.SupplierPurchaseMapper;
import com.procure.response.ApiResponse;
import com.procure.service.SupplierPurchaseOrderService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class SupplierPurchaseOrderResource {

    private final SupplierPurchaseOrderService supplierPurchaseOrderService;

    private final SupplierPurchaseMapper supplierDetailsMapper;

    public SupplierPurchaseOrderResource(
        SupplierPurchaseOrderService supplierPurchaseOrderService,
        SupplierPurchaseMapper supplierDetailsMapper
    ) {
        this.supplierPurchaseOrderService = supplierPurchaseOrderService;
        this.supplierDetailsMapper = supplierDetailsMapper;
    }

    @GetMapping("/supplierPurchaseOrderDetails/fetchAllRecords")
    public ResponseEntity<List<SupplierPurchaseOrderDto>> fetchRecords() {
        List<SupplierPurchaseOrder> supplierDetailsList = supplierPurchaseOrderService.fetchRecords();
        List<SupplierPurchaseOrderDto> supplierDetailsDTOS = supplierDetailsList
            .stream()
            .map(supplierDetailsMapper::sourceToDestination)
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(supplierDetailsDTOS);
    }

    @PostMapping("/supplierPurchaseOrderDetails")
    public ResponseEntity<ApiResponse> createSupplier(@RequestBody SupplierPurchaseOrderDto supplierPurchaseOrderDto) {
        log.debug("Rest Request to save supplierPurchaseOrderDto{} ", supplierPurchaseOrderDto);
        this.supplierPurchaseOrderService.save(supplierDetailsMapper.destinationToSource(supplierPurchaseOrderDto));
        return new ResponseEntity<>(
            ApiResponse.getSuccessResponse("Supplier Purchase Order details has been added successfully."),
            HttpStatus.OK
        );
    }

    @PutMapping("/supplierPurchaseOrderDetails")
    public ResponseEntity<ApiResponse> updateSupplierDetails(@Valid @RequestBody SupplierPurchaseOrderDto supplierPurchaseOrderDto) {
        log.debug("Rest Request to update SupplierDetails{} ", supplierPurchaseOrderDto);
        this.supplierPurchaseOrderService.save(supplierDetailsMapper.destinationToSource(supplierPurchaseOrderDto));
        return new ResponseEntity<>(
            ApiResponse.getSuccessResponse("Supplier Purchase Order details  has been updated successfully."),
            HttpStatus.OK
        );
    }

    @DeleteMapping("/supplierPurchaseOrderDetails/{id}")
    public ResponseEntity<ApiResponse> deleteSku(@PathVariable Long id) {
        log.debug("Rest Request to delete Supplier Purchase Order details by ID :: {} ", id);
        this.supplierPurchaseOrderService.deleteRecord(id);
        return new ResponseEntity<>(
            ApiResponse.getSuccessResponse("Supplier Purchase Order details has been deleted successfully."),
            HttpStatus.OK
        );
    }
}
