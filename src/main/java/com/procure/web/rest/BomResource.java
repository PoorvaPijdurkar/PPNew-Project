package com.procure.web.rest;

import com.procure.domain.Bom;
import com.procure.dto.BomDto;
import com.procure.mapper.BomMapper;
import com.procure.response.ApiResponse;
import com.procure.service.BomCriteria;
import com.procure.service.BomCriteriaQueryService;
import com.procure.service.BomService;
import com.procure.util.CommonUtil;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/bom")
public class BomResource {

    private final BomService bomService;

    private final BomMapper bomMapper;

    private final BomCriteriaQueryService bomCriteriaQueryService;

    public BomResource(BomService bomService, BomMapper bomMapper, BomCriteriaQueryService bomCriteriaQueryService) {
        this.bomService = bomService;
        this.bomMapper = bomMapper;
        this.bomCriteriaQueryService = bomCriteriaQueryService;
    }

    @GetMapping("/fetchAllRecords")
    public List<Bom> fetchAllRecords() {
        return bomService.fetchAllRecords();
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> createOrders(@Valid @RequestBody Bom bom) {
        bomService.save(bom);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Bom details has been added successfully."), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateBom(@RequestBody BomDto bomDto) {
        bomService.update(bomMapper.destinationToSource(bomDto));
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Bom details has been updated successfully"), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> delete(@RequestParam Long id) {
        bomService.delete(id);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Bom details has been deleted successfully."), HttpStatus.OK);
    }

    @GetMapping("/fetchAllByCriteria")
    public ResponseEntity<List<Bom>> fetchAllByCriteria(@ParameterObject BomCriteria bomCriteria) {
        List<Bom> bomList = bomCriteriaQueryService.findByCriteria(bomCriteria);
        return ResponseEntity.ok().body(bomList);
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<BomDto>> fetchAllByCriteria(@ParameterObject BomCriteria criteria, @ParameterObject Pageable pageable) {
        Page<Bom> page = bomCriteriaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = CommonUtil.generatePaginationHttpHeader(page);
        List<BomDto> bomDtoList = page.getContent().stream().map(bomMapper::sourceToDestination).collect(Collectors.toList());
        return ResponseEntity.ok().headers(headers).body(bomDtoList);
    }

    @GetMapping("/getBomsDetails")
    public List<Map<String, String>> getDetails(@Param("bomId") long bomId) {
        return bomService.getData(bomId);
    }
}
