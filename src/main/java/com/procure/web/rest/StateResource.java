package com.procure.web.rest;

import com.procure.domain.State;
import com.procure.dto.StateDto;
import com.procure.mapper.StateMapper;
import com.procure.response.ApiResponse;
import com.procure.service.StateService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/state/")
public class StateResource {

    private final StateService stateService;
    private final StateMapper stateMapper;

    public StateResource(StateService stateService, StateMapper stateMapper) {
        this.stateService = stateService;
        this.stateMapper = stateMapper;
    }

    @GetMapping("/fetchAllRecords")
    public List<State> fetchAllStateRecords() {
        return stateService.fetchAllStateRecords();
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@RequestBody StateDto stateDto) {
        State state = stateMapper.destinationToSource(stateDto);
        stateService.save(state);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("State details has been saved successfully"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable("id") Long id) {
        stateService.deleteByStateId(id);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("State details has been removed successfully"), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> update(@RequestBody StateDto stateDto) {
        State state = stateMapper.destinationToSource(stateDto);
        stateService.save(state);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("State details has been updated Successfully"), HttpStatus.OK);
    }

    @GetMapping("/fetchStateRecordsByCountryCode/{countryCode}")
    public ResponseEntity<List<State>> fetchStateRecords(@PathVariable("countryCode") Long countryCode) {
        List<State> stateList = stateService.fetchStateRecordsByCountryCode(countryCode);
        return new ResponseEntity<>(stateList, HttpStatus.OK);
    }
}
