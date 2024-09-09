package com.procure.web.rest;

import com.procure.domain.CustomerDetails;
import com.procure.dto.CustomerDetailsDto;
import com.procure.mapper.CustomerDetailsMapper;
import com.procure.response.ApiResponse;
import com.procure.service.CustomerDetailsCriteria;
import com.procure.service.CustomerDetailsCriteriaService;
import com.procure.service.CustomerDetailsService;
import com.procure.util.CommonUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/customerDetails/")
public class CustomerDetailsResource {

    private final CustomerDetailsService customerDetailsService;

    private final CustomerDetailsMapper customerDetailsMapper;

    private final CustomerDetailsCriteriaService customerDetailsCriteriaService;

    public CustomerDetailsResource(
        CustomerDetailsService customerDetailsService,
        CustomerDetailsMapper customerDetailsMapper,
        CustomerDetailsCriteriaService customerDetailsCriteriaService
    ) {
        this.customerDetailsService = customerDetailsService;
        this.customerDetailsMapper = customerDetailsMapper;
        this.customerDetailsCriteriaService = customerDetailsCriteriaService;
    }

    @GetMapping("/fetchAllRecords")
    public List<CustomerDetails> fetchAllCustomerDetailsRecords() {
        return customerDetailsService.fetchAllCustomerRecords();
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@RequestBody CustomerDetailsDto customerDetailsDto) {
        CustomerDetails customerDetails = customerDetailsMapper.destinationToSource(customerDetailsDto);
        customerDetailsService.save(customerDetails);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Customer details has been updated successfully"), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> update(@RequestBody CustomerDetailsDto customerDetailsDto) {
        CustomerDetails customerDetails = customerDetailsMapper.destinationToSource(customerDetailsDto);
        customerDetailsService.save(customerDetails);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Customer details has been updated successfully"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("id") Long id) {
        customerDetailsService.deleteCustomerDetailsById(id);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Customer details has been deleted successfully"), HttpStatus.OK);
    }

    @GetMapping("/fetchCustomerRecordsByCriteria")
    public ResponseEntity<List<CustomerDetailsDto>> fetchAllByCriteria(@ParameterObject CustomerDetailsCriteria customerDetailsCriteria) {
        List<CustomerDetails> customerDetailsList = customerDetailsCriteriaService.findByCriteria(customerDetailsCriteria);
        List<CustomerDetailsDto> customerDetailsDtoList = customerDetailsList
            .stream()
            .map(customerDetailsMapper::sourceToDestination)
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(customerDetailsDtoList);
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<CustomerDetailsDto>> fetchAllByCriteria(
        @ParameterObject CustomerDetailsCriteria customerDetailsCriteria,
        @ParameterObject Pageable pageable
    ) {
        Page<CustomerDetails> page = customerDetailsCriteriaService.findByCriteria(customerDetailsCriteria, pageable);
        HttpHeaders headers = CommonUtil.generatePaginationHttpHeader(page);
        List<CustomerDetailsDto> customerDetailsDtoList = page
            .getContent()
            .stream()
            .map(customerDetailsMapper::sourceToDestination)
            .collect(Collectors.toList());
        return ResponseEntity.ok().headers(headers).body(customerDetailsDtoList);
    }
}
