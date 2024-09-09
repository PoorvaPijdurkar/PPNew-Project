package com.procure.web.rest;

import com.procure.domain.Production;
import com.procure.domain.VmProductionReport;
import com.procure.dto.ProductionDto;
import com.procure.mapper.ProductionMapper;
import com.procure.response.ApiResponse;
import com.procure.service.ProductionService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/production")
public class ProductionResource {

    private final ProductionService productionService;

    private final ProductionMapper productionMapper;

    public ProductionResource(ProductionService productionService, ProductionMapper productionMapper) {
        this.productionService = productionService;
        this.productionMapper = productionMapper;
    }

    @GetMapping("/fetchAllRecords")
    public List<Production> fetchAllRecords() {
        return productionService.fetchAllRecords();
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> create(@RequestBody Production production) {
        productionService.save(production);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Production details has been added successfully."), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> update(@RequestBody ProductionDto productionDto) {
        productionService.save(productionMapper.destinationToSource(productionDto));
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Production details has been updated successfully."), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("id") long id) {
        productionService.deleteProductionRecord(id);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Production details has been deleted successfully."), HttpStatus.OK);
    }

    @PostMapping("/generateProductionReport/{batchNumber}")
    public ResponseEntity<InputStreamResource> generateProductionReport(@PathVariable("batchNumber") String batchNumber)
        throws IOException {
        byte[] content = productionService.generateProductionInvoice(batchNumber);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
        InputStreamResource inputStreamResource = new InputStreamResource(byteArrayInputStream);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=production-report.pdf");
        return ResponseEntity
            .ok()
            .headers(httpHeaders)
            .contentLength(content.length)
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(inputStreamResource);
    }

    @GetMapping("/generateProductionData/{bomId}")
    public List<Map<String, String>> calculateProductionData(@PathVariable("bomId") Long bomId, @RequestParam("batchSize") long batchSize) {
        return productionService.calculateProductionData(bomId, batchSize);
    }

    @GetMapping("/getProductionDataByItemCode/{batchNumber}")
    public List<VmProductionReport> getProductionDataByItemCode(@PathVariable("batchNumber") String batchNumber) {
        return productionService.getProductionDataByItemCode(batchNumber);
    }
}
