package com.procure.mapper;

import com.procure.domain.Department;
import com.procure.dto.DepartmentDto;
import org.mapstruct.Mapper;

@Mapper
public interface DepartmentMapper {
    DepartmentDto sourceToDestination(Department department);
    Department destinationToSource(DepartmentDto departmentDto);
}
