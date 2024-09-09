package com.procure.web.rest;

import com.procure.domain.Entitlements;
import com.procure.dto.EntitlementsDto;
import com.procure.mapper.EntitlementsMapper;
import com.procure.response.ApiResponse;
import com.procure.service.EntitlementsCriteria;
import com.procure.service.EntitlementsQueryService;
import com.procure.service.EntitlementsService;
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
@RequestMapping("/api/entitlements")
@Slf4j
public class EntitlementsResource {

    private final EntitlementsService entitlementsService;
    private final EntitlementsQueryService entitlementsQueryService;

    private final EntitlementsMapper entitlementsMapper;

    public EntitlementsResource(
        EntitlementsService entitlementsService,
        EntitlementsQueryService entitlementsQueryService,
        EntitlementsMapper entitlementsMapper
    ) {
        this.entitlementsService = entitlementsService;
        this.entitlementsQueryService = entitlementsQueryService;
        this.entitlementsMapper = entitlementsMapper;
    }

    @GetMapping("/fetchAllRecords")
    public ResponseEntity<List<EntitlementsDto>> fetchRecords() {
        List<Entitlements> entitlementsList = entitlementsService.fetchRecords();
        List<EntitlementsDto> entitlementsDtos = entitlementsList
            .stream()
            .map(entitlementsMapper::sourceToDestination)
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(entitlementsDtos);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countDepartment(@ParameterObject @Valid EntitlementsCriteria criteria) {
        log.debug("Rest Request to count Orders by Criteria : {} ", criteria);
        return ResponseEntity.ok().body(entitlementsQueryService.countByCriteria(criteria));
    }

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<ApiResponse> deleteDepartment(@PathVariable Long id) {
        log.debug("Rest Request to delete Orders by ItemCode :: {} ", id);
        entitlementsService.deleteRecord(id);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Entitlements details has been deleted successfully."), HttpStatus.OK);
    }

    @PutMapping("/entitlements")
    public ResponseEntity<ApiResponse> updateOrders(@Valid @RequestBody EntitlementsDto entitlementsDto) {
        log.debug("Rest Request to update Orders{} ", entitlementsDto);
        entitlementsService.save(entitlementsMapper.destinationToSource(entitlementsDto));
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Entitlements details has been updated successfully."), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createEntitlements(@Valid @RequestBody EntitlementsDto entitlementsDto) {
        log.debug("Rest Request to save Orders{} ", entitlementsDto);
        Entitlements entitlements = entitlementsMapper.destinationToSource(entitlementsDto);
        entitlementsService.save(entitlements);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Orders details has been added successfully."), HttpStatus.OK);
    }

    @GetMapping("/fetchAllByCriteria")
    public ResponseEntity<List<EntitlementsDto>> fetchAllByCriteria(@ParameterObject EntitlementsCriteria entitlementsCriteria) {
        log.debug("REST request to get a page of Entitlement by criteria: {}", entitlementsCriteria);
        List<Entitlements> entitlementsList = entitlementsQueryService.findByCriteria(entitlementsCriteria);
        List<EntitlementsDto> entitlementsDtos = entitlementsList
            .stream()
            .map(entitlementsMapper::sourceToDestination)
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(entitlementsDtos);
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<EntitlementsDto>> fetchAllByCriteria(
        @ParameterObject EntitlementsCriteria criteria,
        @ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of Entitlements by criteria: {}", criteria);
        Page<Entitlements> page = entitlementsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = CommonUtil.generatePaginationHttpHeader(page);
        List<EntitlementsDto> entitlementsDtoList = page
            .getContent()
            .stream()
            .map(entitlementsMapper::sourceToDestination)
            .collect(Collectors.toList());
        return ResponseEntity.ok().headers(headers).body(entitlementsDtoList);
    }
}
