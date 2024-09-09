package com.procure.service;

import static org.springframework.http.ResponseEntity.ok;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.procure.domain.Sku;
import com.procure.repository.SkuRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@Slf4j
public class SkuService {

    private final SkuRepository skuRepository;
    private Sku sku;

    public SkuService(SkuRepository skuRepository) {
        this.skuRepository = skuRepository;
    }

    public List<Sku> fetchRecords() {
        return this.skuRepository.findAll();
    }

    public Sku save(Sku sku) {
        log.debug("Request to save SKU :: {} ", sku);
        return skuRepository.save(sku);
    }

    public void deleteRecord(Long id) {
        this.skuRepository.deleteById(id);
    }

    public ResponseEntity<ByteArrayResource> extractFile(MultipartFile file) throws IOException {
        ResponseEntity<ByteArrayResource> resourceResponseEntity = null;

        long fileSizeInBytes = file.getSize();
        long fileSizeInKb = fileSizeInBytes / 1000;

        if (fileSizeInKb <= 500) {
            String fileExtensions = FileNameUtils.getExtension(file.getOriginalFilename());

            if (fileExtensions.equalsIgnoreCase("csv")) {
                resourceResponseEntity = handelCsv(file);
            } else if (fileExtensions.equalsIgnoreCase("xlsx")) {
                resourceResponseEntity = handleXlsx(file);
            } else {
                throw new RuntimeException("File extension is not supported, please follow(.csv,.xlsx) file extension");
            }
        } else {
            throw new RuntimeException("File size is too big");
        }
        return resourceResponseEntity;
    }

    public ResponseEntity<ByteArrayResource> handelCsv(MultipartFile file) throws IOException {
        ResponseEntity<ByteArrayResource> byteArrayResourceResponseEntity = null;
        Path tempFilePath = Files.createTempFile("temp-sku-", ".csv");
        try (CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(file.getInputStream())))) {
            List<String[]> lines = reader.readAll();

            List<String[]> dataLines = lines.subList(1, lines.size());

            List<Sku> dataList = dataLines.stream().map(line -> new Sku(line[0], line[1], line[2], line[3])).collect(Collectors.toList());

            List<Sku> skuList = this.skuRepository.findAll();
            List<String> orignalSkuList = skuList
                .stream()
                .map(item -> item.getProductName().toUpperCase(Locale.ROOT))
                .collect(Collectors.toList());
            List<String[]> list = new ArrayList<>();
            for (Sku sku : dataList) {
                if (orignalSkuList.contains(sku.getProductName().toUpperCase())) {
                    duplicateRecordValidation(tempFilePath, list, sku);
                } else {
                    noDuplicateRecordValidation(tempFilePath, list, sku);
                }
            }
        } catch (CsvException e) {}

        byte[] data = Files.readAllBytes(tempFilePath);
        ByteArrayResource resource = new ByteArrayResource(data);
        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"products_data.csv\"");

        byteArrayResourceResponseEntity =
            ok().contentLength(data.length).headers(headers).contentType(MediaType.parseMediaType("text/csv")).body(resource);

        return byteArrayResourceResponseEntity;
    }

    public ResponseEntity<ByteArrayResource> handleXlsx(MultipartFile file) throws IOException {
        ResponseEntity<ByteArrayResource> resourceResponseEntity = null;
        Path tempFilePath = Files.createTempFile("temp-sku-", ".xlsx");
        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            List<String[]> list = new ArrayList<>();

            Sku skuData = new Sku();
            int rowNum = 1;

            List<Sku> listOfSkus = skuRepository.findAll();

            List<String> stringList = listOfSkus
                .stream()
                .map(value -> value.getProductName().toUpperCase(Locale.ROOT))
                .collect(Collectors.toList());

            while (rowNum <= sheet.getLastRowNum()) {
                XSSFRow row = sheet.getRow(rowNum);

                row.getCell(0).setCellType(CellType.STRING);
                XSSFCell productNameCell = row.getCell(0);
                String productName = productNameCell.getStringCellValue();
                skuData.setProductName(productName);

                row.getCell(1).setCellType(CellType.STRING);
                XSSFCell productTypeCell = row.getCell(1);
                String productType = productTypeCell.getStringCellValue();
                skuData.setProductType(productType);

                row.getCell(2).setCellType(CellType.STRING);
                XSSFCell productCodeCell = row.getCell(2);
                String productCode = productCodeCell.getStringCellValue();
                skuData.setProductCode(productCode);

                row.getCell(3).setCellType(CellType.STRING);
                XSSFCell unitOfMeasureCell = row.getCell(3);
                String unitOfMeasure = unitOfMeasureCell.getStringCellValue();
                skuData.setUnitOfMeasure(unitOfMeasure);

                if (stringList.contains(skuData.getProductName().toUpperCase())) {
                    duplicateRecordValidation(tempFilePath, list, skuData);
                } else {
                    noDuplicateRecordValidation(tempFilePath, list, skuData);
                }
                rowNum++;
            }
            byte[] data = Files.readAllBytes(tempFilePath);
            ByteArrayResource resource = new ByteArrayResource(data);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + ".csv");

            return (
                resourceResponseEntity =
                    ResponseEntity
                        .ok()
                        .contentLength(data.length)
                        .headers(headers)
                        .contentType(MediaType.parseMediaType("text/csv"))
                        .body(resource)
            );
        }
    }

    private void duplicateRecordValidation(Path tempFilePath, List<String[]> list, Sku sku1) {
        String msg = "Product  " + sku1.getProductName() + " is already exist in system";

        try (CSVWriter writer = new CSVWriter(Files.newBufferedWriter(tempFilePath, StandardCharsets.UTF_8))) {
            String[] header = { "product_name", "product_type", "product_code", "unit_of_measure", "message" };
            writer.writeNext(header);
            sku1.setMessage(msg);
            String data[] = {
                sku1.getProductName(),
                sku1.getProductType(),
                sku1.getProductCode(),
                sku1.getUnitOfMeasure(),
                sku1.getMessage(),
            };
            list.add(data);
            writer.writeAll(list);
        } catch (IOException e) {}
    }

    private void noDuplicateRecordValidation(Path tempFilePath, List<String[]> list, Sku sku1) {
        String msg = "Success";
        try (CSVWriter writer = new CSVWriter(Files.newBufferedWriter(tempFilePath, StandardCharsets.UTF_8))) {
            String[] header = { "product_name", "product_type", "product_code", "unit_of_measure", "message" };
            writer.writeNext(header);
            sku1.setMessage(msg);
            String data[] = {
                sku1.getProductName(),
                sku1.getProductType(),
                sku1.getProductCode(),
                sku1.getUnitOfMeasure(),
                sku1.getMessage(),
            };
            list.add(data);

            writer.writeAll(list);
        } catch (IOException e) {}
        this.skuRepository.save(sku1);
    }
}
