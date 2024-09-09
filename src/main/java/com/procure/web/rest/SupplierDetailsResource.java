package com.procure.web.rest;

import com.procure.domain.SupplierDetails;
import com.procure.dto.SupplierDetailsDTO;
import com.procure.mapper.SupplierDetailsMapper;
import com.procure.response.ApiResponse;
import com.procure.service.SupplierDetailsCriteria;
import com.procure.service.SupplierDetailsQueryService;
import com.procure.service.SupplierService;
import com.procure.util.CommonUtil;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class SupplierDetailsResource {

    private final SupplierService supplierService;

    private final SupplierDetailsQueryService supplierDetailsQueryService;

    private final SupplierDetailsMapper supplierDetailsMapper;

    public SupplierDetailsResource(
        SupplierService supplierService,
        SupplierDetailsQueryService supplierDetailsQueryService,
        SupplierDetailsMapper supplierDetailsMapper
    ) {
        this.supplierService = supplierService;
        this.supplierDetailsQueryService = supplierDetailsQueryService;
        this.supplierDetailsMapper = supplierDetailsMapper;
    }

    @GetMapping("/supplier-details/fetchAllRecords")
    public ResponseEntity<List<SupplierDetailsDTO>> fetchRecords() {
        List<SupplierDetails> supplierDetailsList = supplierService.fetchRecords();
        List<SupplierDetailsDTO> supplierDetailsDTOS = supplierDetailsList
            .stream()
            .map(supplierDetailsMapper::sourceToDestination)
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(supplierDetailsDTOS);
    }

    @GetMapping("/supplier-details/fetchAllByCriteria")
    public ResponseEntity<List<SupplierDetailsDTO>> fetchAllByCriteria(@ParameterObject SupplierDetailsCriteria criteria) {
        log.debug("REST request to get a page of SupplierDetails by criteria: {}", criteria);
        List<SupplierDetails> supplierDetails = supplierDetailsQueryService.findByCriteria(criteria);
        List<SupplierDetailsDTO> supplierDetailsDTOS = supplierDetails
            .stream()
            .map(supplierDetailsMapper::sourceToDestination)
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(supplierDetailsDTOS);
    }

    @GetMapping("/supplier-details/fetchAll")
    public ResponseEntity<List<SupplierDetailsDTO>> fetchAllByCriteria(
        @ParameterObject SupplierDetailsCriteria criteria,
        @ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of SupplierDetailsDTO by criteria: {}", criteria);
        Page<SupplierDetails> page = supplierDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = CommonUtil.generatePaginationHttpHeader(page);
        List<SupplierDetailsDTO> supplierDetailsDTOS = page
            .getContent()
            .stream()
            .map(supplierDetailsMapper::sourceToDestination)
            .collect(Collectors.toList());
        return ResponseEntity.ok().headers(headers).body(supplierDetailsDTOS);
    }

    @GetMapping("/supplier-details/count")
    public ResponseEntity<Long> countSupplierDetails(@ParameterObject @Valid SupplierDetailsCriteria criteria) {
        log.debug("Rest Request to count SupplierDetails by Criteria : {} ", criteria);
        return ResponseEntity.ok().body(supplierDetailsQueryService.countByCriteria(criteria));
    }

    @PostMapping("/supplier-details")
    public ResponseEntity<ApiResponse> createSupplier(@RequestBody SupplierDetailsDTO supplierDetailsDTO) {
        log.debug("Rest Request to save SupplierDTO{} ", supplierDetailsDTO);
        supplierService.save(supplierDetailsMapper.destinationToSource(supplierDetailsDTO));
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Supplier details has been added successfully."), HttpStatus.OK);
    }

    @PutMapping("/supplier-details")
    public ResponseEntity<ApiResponse> updateSupplierDetails(@Valid @RequestBody SupplierDetailsDTO supplierDetailsDTO) {
        log.debug("Rest Request to update SupplierDetails{} ", supplierDetailsDTO);
        supplierService.save(supplierDetailsMapper.destinationToSource(supplierDetailsDTO));
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Supplier details has been updated successfully."), HttpStatus.OK);
    }

    @DeleteMapping("/supplier-details/delete/{id}")
    public ResponseEntity<ApiResponse> deleteSku(@PathVariable Long id) {
        log.debug("Rest Request to delete SupplierDetails by ID :: {} ", id);
        supplierService.deleteRecord(id);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Supplier details has been deleted successfully."), HttpStatus.OK);
    }

    @PostMapping("/supplier-details/createAll")
    public ResponseEntity<ApiResponse> createAllSupplierDetails(@Valid @RequestBody List<SupplierDetailsDTO> supplierDetailsDTOS) {
        log.debug("Rest Request to save Supplier Details{} ", supplierDetailsDTOS);
        List<SupplierDetails> supplierDetails = supplierDetailsDTOS
            .stream()
            .map(supplierDetailsMapper::destinationToSource)
            .collect(Collectors.toList());

        supplierService.saveAll(supplierDetails);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Supplier  details has been added successfully."), HttpStatus.OK);
    }
}
