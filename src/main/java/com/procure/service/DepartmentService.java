package com.procure.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.procure.domain.Department;
import com.procure.repository.DepartmentRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@Slf4j
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    private Department department;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> fetchRecords() {
        return this.departmentRepository.findAll();
    }

    public void deleteRecord(Long departmentId) {
        this.departmentRepository.deleteById(departmentId);
    }

    public Department save(Department department) {
        log.debug("Request to save Department :: {} ", department);
        return departmentRepository.save(department);
    }

    public void extractFile(MultipartFile file) throws IOException {
        try (CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(file.getInputStream())))) {
            List<String[]> lines = reader.readAll();
            List<String[]> dataLines = lines.subList(1, lines.size());

            List<Department> dataList = dataLines.stream().map(line -> new Department(line[0], line[1])).collect(Collectors.toList());
            List<Department> departmentList = this.departmentRepository.findAll();
            List<String> originalDepartmentList = departmentList
                .stream()
                .map(item -> item.getDepartmentName().toUpperCase(Locale.ROOT))
                .collect(Collectors.toList());
            for (Department department1 : dataList) {
                if (originalDepartmentList.contains(department1.getDepartmentName().toUpperCase())) {
                    log.info("Duplicate Found");
                } else {
                    this.departmentRepository.save(department1);
                }
            }
        } catch (IOException | CsvException e) {}
    }
}
