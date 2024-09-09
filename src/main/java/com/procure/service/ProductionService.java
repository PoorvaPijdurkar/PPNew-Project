package com.procure.service;

import com.procure.domain.Production;
import com.procure.domain.VmProductionReport;
import com.procure.repository.ProductionRepository;
import com.procure.repository.VmProductionReportRepository;
import com.procure.util.CommonUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ProductionService {

    private final ProductionRepository productionRepository;
    private final UserService userService;

    private final VmProductionReportRepository vmProductionReportRepository;

    public ProductionService(
        ProductionRepository productionRepository,
        UserService userService,
        VmProductionReportRepository vmProductionReportRepository
    ) {
        this.productionRepository = productionRepository;
        this.userService = userService;
        this.vmProductionReportRepository = vmProductionReportRepository;
    }

    public List<Production> fetchAllRecords() {
        return productionRepository.findAll();
    }

    public Production save(Production production) {
        String batchNumber = this.generateBatchNumber();
        production.setBatchNumber(batchNumber);
        production.setCreatedDate(LocalDate.now());
        return productionRepository.save(production);
    }

    private String generateBatchNumber() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmmss");
        return ft.format(dNow);
    }

    public void deleteProductionRecord(long id) {
        productionRepository.deleteById(id);
    }

    public List<Map<String, String>> calculateProductionData(Long bomId, Long batchSize) {
        List<Map<String, String>> productDetails = productionRepository.getDetails(bomId);
        return this.calculateProductQuantity(productDetails, batchSize);
    }

    private List<Map<String, String>> calculateProductQuantity(List<Map<String, String>> productDetails, Long batchSize) {
        List<Map<String, String>> mapList = new ArrayList<>();
        for (Map<String, String> map : productDetails) {
            Map<String, String> productMap = new HashMap<>();
            if (map.containsKey("quantity") && Objects.nonNull(map.get("quantity"))) {
                BigDecimal productQuantity = new BigDecimal(map.get("quantity"));
                productMap.put("productName", map.get("productName"));
                productMap.put("productType", map.get("productType"));
                productMap.put("productCode", map.get("productCode"));
                productMap.put("unitOfMeasure", map.get("unitOfMeasure"));
                productMap.put("quantity", map.get("quantity"));
                BigDecimal productQty = CommonUtil.calculateProductionQty(batchSize, productQuantity);

                productMap.put("calculatedQty", productQty.toPlainString());

                mapList.add(productMap);
            }
        }
        return mapList;
    }

    public byte[] generateProductionInvoice(String batchNumber) throws IOException {
        PDDocument pdDocument = new PDDocument();
        PDPage pdPage = new PDPage(PDRectangle.A4);
        pdDocument.addPage(pdPage);
        PDPageContentStream pdPageContentStream = new PDPageContentStream(pdDocument, pdPage);

        Optional<List<VmProductionReport>> optional = vmProductionReportRepository.findByBatchNumber(batchNumber);

        List<VmProductionReport> vmProductionReports = null;

        if (!optional.isEmpty()) {
            vmProductionReports = optional.get();
        }

        if (!CollectionUtils.isEmpty(vmProductionReports)) {
            CommonUtil.setContent(pdPageContentStream, vmProductionReports, pdPage, userService);
            List<Double> productsQuantity = vmProductionReports.stream().map(VmProductionReport::getQuantity).collect(Collectors.toList());
            List<Double> productCalculatedQuantity = vmProductionReports
                .stream()
                .map(VmProductionReport::getCalculatedQty)
                .collect(Collectors.toList());

            List<String> ingredientsList = vmProductionReports
                .stream()
                .map(VmProductionReport::getProductName)
                .collect(Collectors.toList());

            CommonUtil.drawTable(
                pdPageContentStream,
                100,
                600,
                vmProductionReports,
                ingredientsList,
                productsQuantity,
                productCalculatedQuantity,
                pdPage
            );
        }

        pdPageContentStream.close();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        pdDocument.save(byteArrayOutputStream);
        pdDocument.close();
        return byteArrayOutputStream.toByteArray();
    }

    public List<VmProductionReport> getProductionDataByItemCode(String batchNumber) {
        Optional<List<VmProductionReport>> optional = vmProductionReportRepository.findByBatchNumber(batchNumber);
        if (!optional.isEmpty()) {
            return optional.get();
        }
        return new ArrayList<>();
    }
}
