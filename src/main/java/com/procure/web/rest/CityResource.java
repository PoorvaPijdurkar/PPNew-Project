package com.procure.web.rest;

import com.procure.domain.City;
import com.procure.mapper.CityMapper;
import com.procure.response.ApiResponse;
import com.procure.service.CityService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/city/")
public class CityResource {

    private final CityService cityService;

    private final CityMapper cityMapper;

    public CityResource(CityService cityService, CityMapper cityMapper) {
        this.cityService = cityService;
        this.cityMapper = cityMapper;
    }

    @GetMapping("/fetchAllCityRecords")
    public List<City> fetchAllRecords() {
        return cityService.fetchAllCityRecords();
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> update(@RequestBody City city) {
        cityService.save(city);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("City details has been updated successfully"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("id") Long id) {
        cityService.deleteByCityId(id);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("City details has been deleted successfully"), HttpStatus.OK);
    }

    @GetMapping("/fetchCitiesRecordsByStateCode/{stateCode}")
    public ResponseEntity<List<City>> fetchRecords(@PathVariable("stateCode") Long stateCode) {
        List<City> cities = cityService.fetchAllCityRecordsByStateCode(stateCode);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }
}
