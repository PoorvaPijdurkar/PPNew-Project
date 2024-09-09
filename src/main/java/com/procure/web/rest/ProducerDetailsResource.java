package com.procure.web.rest;

import com.procure.domain.ProducerDetails;
import com.procure.dto.ProducerDetailsDTO;
import com.procure.mapper.ProducerDetailsMapper;
import com.procure.response.ApiResponse;
import com.procure.service.ProducerDetailsCriteria;
import com.procure.service.ProducerDetailsQueryService;
import com.procure.service.ProducerDetailsService;
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
@RequestMapping("/api/producer")
@Slf4j
public class ProducerDetailsResource {

    private final ProducerDetailsService producerDetailsService;

    private final ProducerDetailsQueryService producerDetailsQueryService;

    private final ProducerDetailsMapper producerDetailsMapper;

    public ProducerDetailsResource(
        ProducerDetailsService producerDetailsService,
        ProducerDetailsQueryService producerDetailsQueryService,
        ProducerDetailsMapper producerDetailsMapper
    ) {
        this.producerDetailsService = producerDetailsService;
        this.producerDetailsQueryService = producerDetailsQueryService;
        this.producerDetailsMapper = producerDetailsMapper;
    }

    @GetMapping("/fetchAllRecords")
    public ResponseEntity<List<ProducerDetailsDTO>> fetchRecords() {
        log.info("REST request to fetch all records from ProducerDetails table");
        List<ProducerDetails> producerDetailsList = producerDetailsService.fetchRecords();
        List<ProducerDetailsDTO> producerDtoList = producerDetailsList
            .stream()
            .map(producerDetailsMapper::sourceToDestination)
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(producerDtoList);
    }

    @GetMapping("/fetchAllByCriteria")
    public ResponseEntity<List<ProducerDetailsDTO>> fetchAllByCriteria(@ParameterObject ProducerDetailsCriteria producerDetailsCriteria) {
        log.debug("REST request to get a page of ProducerDetails by criteria: {}", producerDetailsCriteria);
        List<ProducerDetails> producerDetailsList = producerDetailsQueryService.findByCriteria(producerDetailsCriteria);
        List<ProducerDetailsDTO> producerDTOTolist = producerDetailsList
            .stream()
            .map(producerDetailsMapper::sourceToDestination)
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(producerDTOTolist);
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<ProducerDetailsDTO>> fetchAllByCriteria(
        @ParameterObject ProducerDetailsCriteria criteria,
        @ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ProducerDetails by criteria: {}", criteria);
        Page<ProducerDetails> page = producerDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = CommonUtil.generatePaginationHttpHeader(page);
        List<ProducerDetailsDTO> producerDTos = page
            .getContent()
            .stream()
            .map(producerDetailsMapper::sourceToDestination)
            .collect(Collectors.toList());
        return ResponseEntity.ok().headers(headers).body(producerDTos);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countProducerDetails(@ParameterObject @Valid ProducerDetailsCriteria criteria) {
        log.debug("Rest Request to count ProducerDetails by Criteria : {} ", criteria);
        return ResponseEntity.ok().body(producerDetailsQueryService.countByCriteria(criteria));
    }

    @PostMapping("/producerDetails")
    public ResponseEntity<ApiResponse> createProducerDetails(@Valid @RequestBody ProducerDetailsDTO producerDetailsDTO) {
        log.debug("Rest Request to save ProducerDetails{} ", producerDetailsDTO);
        producerDetailsService.save(producerDetailsMapper.destinationToSource(producerDetailsDTO));
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("ProducerDetails  details has been added successfully."), HttpStatus.OK);
    }

    @DeleteMapping("/producerDetails/{id}")
    public ResponseEntity<ApiResponse> deleteProducerDetails(@PathVariable Long id) {
        log.debug("Rest Request to delete ProducerDetails by ID :: {} ", id);
        producerDetailsService.deleteRecord(id);
        return new ResponseEntity<>(
            ApiResponse.getSuccessResponse("ProducerDetails details has been deleted successfully."),
            HttpStatus.OK
        );
    }

    @PutMapping("/producerDetails")
    public ResponseEntity<ApiResponse> updateProducerDetails(@Valid @RequestBody ProducerDetailsDTO producerDetailsDTO) {
        log.debug("Rest Request to update ProducerDetails{} ", producerDetailsDTO);
        producerDetailsService.save(producerDetailsMapper.destinationToSource(producerDetailsDTO));
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Producer details has been updated successfully."), HttpStatus.OK);
    }
}
