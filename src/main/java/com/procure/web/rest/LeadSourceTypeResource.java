package com.procure.web.rest;

import com.procure.domain.LeadSourceType;
import com.procure.dto.LeadSourceTypeDto;
import com.procure.mapper.LeadSourceTypeMapper;
import com.procure.response.ApiResponse;
import com.procure.service.LeadSourceTypeService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/leadSourceType")
public class LeadSourceTypeResource {

    private final LeadSourceTypeService leadSourceTypeService;

    private final LeadSourceTypeMapper leadSourceTypeMapper;

    public LeadSourceTypeResource(LeadSourceTypeService leadSourceTypeService, LeadSourceTypeMapper leadSourceTypeMapper) {
        this.leadSourceTypeService = leadSourceTypeService;
        this.leadSourceTypeMapper = leadSourceTypeMapper;
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@RequestBody LeadSourceTypeDto leadSourceTypeDto) {
        LeadSourceType leadSourceType = leadSourceTypeMapper.destinationToSource(leadSourceTypeDto);
        leadSourceTypeService.save(leadSourceType);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Lead source type details has been saved successfully"), HttpStatus.OK);
    }

    @GetMapping("/fetchLeadSourceTypeRecords")
    public List<LeadSourceType> fetchLeadsTypes() {
        return leadSourceTypeService.fetchLeadSourceTypeRecords();
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> update(@RequestBody LeadSourceTypeDto leadSourceTypeDto) {
        LeadSourceType leadSourceType = leadSourceTypeMapper.destinationToSource(leadSourceTypeDto);
        leadSourceTypeService.save(leadSourceType);
        return new ResponseEntity<>(
            ApiResponse.getSuccessResponse("Lead source type details has been updated successfully"),
            HttpStatus.OK
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("id") Long id) {
        leadSourceTypeService.delete(id);
        return new ResponseEntity<>(
            ApiResponse.getSuccessResponse("Lead source type details has been deleted successfully"),
            HttpStatus.OK
        );
    }
}
