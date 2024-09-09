package com.procure.web.rest;

import com.procure.domain.Sku;
import com.procure.dto.SkuDto;
import com.procure.mapper.SkuMapper;
import com.procure.response.ApiResponse;
import com.procure.service.SkuCriteria;
import com.procure.service.SkuQueryService;
import com.procure.service.SkuService;
import com.procure.util.CommonUtil;
import com.procure.web.rest.errors.DuplicateRecordsFoundException;
import com.procure.web.rest.errors.ReferencedEntityDeleteException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@Slf4j
public class SkuResource {

    private final SkuService skuService;

    private final SkuQueryService skuQueryService;

    private final SkuMapper skuMapper;

    public SkuResource(SkuService skuService, SkuQueryService skuQueryService, SkuMapper skuMapper) {
        this.skuService = skuService;
        this.skuQueryService = skuQueryService;
        this.skuMapper = skuMapper;
    }

    @GetMapping("/sku/fetchAllRecords")
    public ResponseEntity<List<SkuDto>> fetchRecords() {
        List<Sku> skuList = skuService.fetchRecords();
        List<SkuDto> skuDtoList = skuList.stream().map(skuMapper::sourceToDestination).collect(Collectors.toList());
        return ResponseEntity.ok().body(skuDtoList);
    }

    @GetMapping("/sku/fetchAllByCriteria")
    public ResponseEntity<List<SkuDto>> fetchAllByCriteria(@ParameterObject SkuCriteria criteria) {
        log.debug("REST request to get a page of SkuDto by criteria: {}", criteria);
        List<Sku> skuList = skuQueryService.findByCriteria(criteria);
        List<SkuDto> skuDtoList = skuList.stream().map(skuMapper::sourceToDestination).collect(Collectors.toList());
        return ResponseEntity.ok().body(skuDtoList);
    }

    @GetMapping("/sku/fetchAll")
    public ResponseEntity<List<SkuDto>> fetchAllByCriteria(@ParameterObject SkuCriteria criteria, @ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SkuDto by criteria: {}", criteria);
        Page<Sku> page = skuQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = CommonUtil.generatePaginationHttpHeader(page);
        List<SkuDto> skuDtoList = page.getContent().stream().map(skuMapper::sourceToDestination).collect(Collectors.toList());
        return ResponseEntity.ok().headers(headers).body(skuDtoList);
    }

    @GetMapping("/sku/count")
    public ResponseEntity<Long> countSkus(@ParameterObject @Valid SkuCriteria criteria) {
        log.debug("Rest Request to count Sku by Criteria : {} ", criteria);
        return ResponseEntity.ok().body(skuQueryService.countByCriteria(criteria));
    }

    @PostMapping("/sku")
    public ResponseEntity<ApiResponse> createSku(@Valid @RequestBody SkuDto skuDto) {
        log.debug("Rest Request to save Sku{} ", skuDto);
        skuService.save(skuMapper.destinationToSource(skuDto));
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Sku details has been added successfully."), HttpStatus.OK);
    }

    @PutMapping("/sku")
    public ResponseEntity<ApiResponse> updateSkuDetails(@Valid @RequestBody SkuDto skuDto) {
        log.debug("Rest Request to update Sku{} ", skuDto);
        skuService.save(skuMapper.destinationToSource(skuDto));
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Sku details has been updated successfully."), HttpStatus.OK);
    }

    @DeleteMapping("/sku/{id}")
    public ResponseEntity<ApiResponse> deleteSku(@PathVariable Long id) {
        log.debug("Rest Request to delete Sku by ID :: {} ", id);
        try {
            skuService.deleteRecord(id);
        } catch (Exception e) {
            throw new ReferencedEntityDeleteException("Cannot delete  entity as it is referenced by other entities");
        }
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Sku details has been deleted successfully."), HttpStatus.OK);
    }

    @PostMapping(value = "/sku/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ByteArrayResource> uploadLocal(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return skuService.extractFile(multipartFile);
    }
}
