package com.procure.web.rest;

import com.procure.domain.Department;
import com.procure.dto.DepartmentDto;
import com.procure.mapper.DepartmentMapper;
import com.procure.repository.DepartmentRepository;
import com.procure.response.ApiResponse;
import com.procure.service.DepartmentCriteria;
import com.procure.service.DepartmentQueryService;
import com.procure.service.DepartmentService;
import com.procure.util.CommonUtil;
import java.util.List;
import java.util.Optional;
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
@RequestMapping("/api/department")
@Slf4j
public class DepartmentResource {

    private final DepartmentService departmentService;
    private final DepartmentQueryService departmentQueryService;

    private final DepartmentMapper departmentMapper;

    private final DepartmentRepository departmentRepository;

    public DepartmentResource(
        DepartmentService departmentService,
        DepartmentQueryService departmentQueryService,
        DepartmentMapper departmentMapper,
        DepartmentRepository departmentRepository
    ) {
        this.departmentService = departmentService;
        this.departmentQueryService = departmentQueryService;
        this.departmentMapper = departmentMapper;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping("/fetchAllRecords")
    public ResponseEntity<List<DepartmentDto>> fetchRecords() {
        List<Department> departmentList = departmentService.fetchRecords();
        List<DepartmentDto> departmentDTOS = departmentList
            .stream()
            .map(departmentMapper::sourceToDestination)
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(departmentDTOS);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countDepartment(@ParameterObject @Valid DepartmentCriteria criteria) {
        log.debug("Rest Request to count departments by Criteria : {} ", criteria);
        return ResponseEntity.ok().body(departmentQueryService.countByCriteria(criteria));
    }

    @DeleteMapping("/Delete/{departmentId}")
    public ResponseEntity<ApiResponse> deleteDepartment(@PathVariable Long departmentId) {
        log.debug("Rest Request to delete departments by ItemCode :: {} ", departmentId);
        departmentService.deleteRecord(departmentId);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Department details has been deleted successfully."), HttpStatus.OK);
    }

    @PutMapping("/department")
    public ResponseEntity<ApiResponse> updateDepartment(@Valid @RequestBody Department department) {
        Optional<Department> department1 = departmentRepository.findById(department.getDepartmentId());
        Department dept = department1.get();

        dept.setDepartmentCode(department.getDepartmentCode());
        dept.setDepartmentName(department.getDepartmentName());

        departmentService.save(dept);

        return new ResponseEntity<>(ApiResponse.getSuccessResponse("Department details has been updated successfully."), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createDepartment(@Valid @RequestBody DepartmentDto departmentDto) {
        log.debug("Rest Request to save departments{} ", departmentDto.toString());
        Department department = departmentMapper.destinationToSource(departmentDto);
        departmentService.save(department);
        return new ResponseEntity<>(ApiResponse.getSuccessResponse("department details has been added successfully."), HttpStatus.OK);
    }

    @GetMapping("/fetchAllByCriteria")
    public ResponseEntity<List<DepartmentDto>> fetchAllByCriteria(@ParameterObject DepartmentCriteria departmentCriteria) {
        log.debug("REST request to get a page of Department by criteria: {}", departmentCriteria);
        List<Department> departmentList = departmentQueryService.findByCriteria(departmentCriteria);
        List<DepartmentDto> departmentDTOS = departmentList
            .stream()
            .map(departmentMapper::sourceToDestination)
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(departmentDTOS);
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<DepartmentDto>> fetchAllByCriteria(
        @ParameterObject DepartmentCriteria criteria,
        @ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of Orders by criteria: {}", criteria);
        Page<Department> page = departmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = CommonUtil.generatePaginationHttpHeader(page);
        List<DepartmentDto> departmentDtoList = page
            .getContent()
            .stream()
            .map(departmentMapper::sourceToDestination)
            .collect(Collectors.toList());
        return ResponseEntity.ok().headers(headers).body(departmentDtoList);
    }
}
