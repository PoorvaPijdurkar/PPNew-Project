package com.procure.web.rest;

import com.procure.domain.Country;
import com.procure.dto.CountryDto;
import com.procure.mapper.CountryMapper;
import com.procure.response.ApiResponse;
import com.procure.service.CountryService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/country/")
public class CountryResource {

    private final CountryService countryService;

    private final CountryMapper countryMapper;

    public CountryResource(CountryService countryService, CountryMapper countryMapper) {
        this.countryService = countryService;
        this.countryMapper = countryMapper;
    }

    @GetMapping("/fetchAllRecords")
    public List<Country> fetchAllRecords() {
        return countryService.fetchAllRecords();
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@RequestBody CountryDto countryDto) {
        Country country = countryMapper.destinationToSource(countryDto);
        countryService.save(country);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Country details has been saved successfully"), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> update(@RequestBody CountryDto countryDto) {
        Country country = countryMapper.destinationToSource(countryDto);
        countryService.save(country);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Country details has been updated successfully"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable("id") Long id) {
        countryService.deleteCountryRecordById(id);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Country details has been updated successfully"), HttpStatus.OK);
    }

    @GetMapping("/fetchCountryRecordsByCountryCode/{countryCode}")
    public ResponseEntity<List<Country>> fetchCountryRecords(@PathVariable("countryCode") String countryCode) {
        List<Country> countryList = countryService.fetchCountryRecordsByCountryCode(countryCode);
        return new ResponseEntity<>(countryList, HttpStatus.OK);
    }
}
