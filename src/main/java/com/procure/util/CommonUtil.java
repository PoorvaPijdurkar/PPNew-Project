package com.procure.util;

import com.procure.domain.VmProductionReport;
import com.procure.service.UserService;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

public class CommonUtil {

    public static final String HEADER_X_TOTAL_COUNT = "X-Total-Count";

    public static <T> HttpHeaders generatePaginationHttpHeader(Page<T> page) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HEADER_X_TOTAL_COUNT, Long.toString(page.getTotalElements()));
        return httpHeaders;
    }

    public static BigDecimal calculateProductionQty(Long batchSize, BigDecimal qty) {
        if (qty != null && batchSize != null) {
            BigDecimal result = (qty.multiply(new BigDecimal(batchSize)));
            return result.divide(new BigDecimal(100));
        }
        return new BigDecimal(0);
    }

    public static String getDate() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return localDate.format(dateTimeFormatter);
    }

    public static void setContent(
        PDPageContentStream pdPageContentStream,
        List<VmProductionReport> productionReports,
        PDPage pdPage,
        UserService userService
    ) throws IOException {
        pdPageContentStream.beginText();
        PDFont pdFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
        pdPageContentStream.setFont(pdFont, 13);
        pdPageContentStream.setLeading(13.5f);
        pdPageContentStream.newLineAtOffset(100, 750);

        pdPageContentStream.showText("Batch Sheet: " + productionReports.get(0).getItemName());
        pdPageContentStream.newLine();
        pdPageContentStream.newLine();
        pdPageContentStream.showText("Date: " + CommonUtil.getDate());
        pdPageContentStream.newLine();
        pdPageContentStream.newLine();
        pdPageContentStream.showText("Name Of Product: " + productionReports.get(0).getItemName());
        pdPageContentStream.newLine();
        pdPageContentStream.newLine();
        pdPageContentStream.showText("Batch Size: " + productionReports.get(0).getBatchSize());
        pdPageContentStream.newLine();
        pdPageContentStream.newLine();
        pdPageContentStream.showText("Batch Code: " + productionReports.get(0).getBatchNumber());
        pdPageContentStream.newLine();
        pdPageContentStream.newLine();
        pdPageContentStream.showText(
            "Weighing done by: " +
            userService.getUserWithAuthorities().get().getFirstName() +
            " " +
            userService.getUserWithAuthorities().get().getLastName()
        );
        pdPageContentStream.endText();
    }

    public static void drawTable(
        PDPageContentStream contentStream,
        float startX,
        float startY,
        List<VmProductionReport> productionReports,
        List<String> ingredientsList,
        List<Double> productsQuantity,
        List<Double> productCalculatedQuantity,
        PDPage pdPage
    ) throws IOException {
        PDFont pdFont = new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD);
        contentStream.setFont(pdFont, 12);

        float pageWidth = pdPage.getMediaBox().getWidth();
        float margin = 50;
        float tableWidth = pageWidth - 2 * margin;
        float rowHeight = 30;
        double total = productCalculatedQuantity.stream().mapToDouble(Double::doubleValue).sum();

        startX = margin;

        float nextY = startY;

        drawTableRow(
            contentStream,
            startX,
            nextY,
            tableWidth,
            rowHeight,
            new String[] { "Sr. No", "Product Name", "Calc Qty", "Weighted Qty", "Batch Number" }
        );
        nextY -= rowHeight;

        int index = 1;
        for (String ingredient : ingredientsList) {
            drawTableRow(
                contentStream,
                startX,
                nextY,
                tableWidth,
                rowHeight,
                new String[] { String.valueOf(index), ingredient, String.valueOf(productCalculatedQuantity.get(index - 1)), "", "" }
            );
            nextY -= rowHeight;
            index++;
        }

        drawTotalRow(contentStream, startX, nextY, tableWidth, rowHeight, total);

        drawApprovedBy(contentStream, startX + tableWidth, nextY - rowHeight, "Approved by");
    }

    public static void drawTableRow(
        PDPageContentStream contentStream,
        float startX,
        float y,
        float tableWidth,
        float rowHeight,
        String[] cells
    ) throws IOException {
        float[] columnWidths = { tableWidth * 0.1f, tableWidth * 0.3f, tableWidth * 0.2f, tableWidth * 0.2f, tableWidth * 0.2f };

        float nextX = startX;

        for (int i = 0; i < cells.length; i++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(nextX + 5, y - rowHeight + 10); // Adjust text offset for better alignment
            contentStream.showText(cells[i]);
            contentStream.endText();
            nextX += columnWidths[i];
        }

        float nextXForBorder = startX;
        for (int i = 0; i <= cells.length; i++) {
            contentStream.moveTo(nextXForBorder, y);
            contentStream.lineTo(nextXForBorder, y - rowHeight);
            contentStream.stroke();
            if (i < cells.length) {
                nextXForBorder += columnWidths[i];
            }
        }

        contentStream.moveTo(startX, y);
        contentStream.lineTo(startX + tableWidth, y);
        contentStream.stroke();
        contentStream.moveTo(startX, y - rowHeight);
        contentStream.lineTo(startX + tableWidth, y - rowHeight);
        contentStream.stroke();
    }

    public static void drawTotalRow(
        PDPageContentStream contentStream,
        float startX,
        float y,
        float tableWidth,
        float rowHeight,
        double total
    ) throws IOException {
        float[] columnWidths = { tableWidth * 0.1f, tableWidth * 0.3f, tableWidth * 0.2f, tableWidth * 0.2f, tableWidth * 0.2f };

        float nextX = startX;

        contentStream.beginText();
        contentStream.newLineAtOffset(nextX + 5, y - rowHeight + 10);
        contentStream.showText("");
        contentStream.endText();
        nextX += columnWidths[0];

        contentStream.beginText();
        contentStream.newLineAtOffset(nextX + 5, y - rowHeight + 10);
        contentStream.showText("");
        contentStream.endText();
        nextX += columnWidths[1];

        contentStream.beginText();
        contentStream.newLineAtOffset(nextX + 5, y - rowHeight + 10);
        contentStream.showText(String.valueOf(total));
        contentStream.endText();
        nextX += columnWidths[2];

        contentStream.beginText();
        contentStream.newLineAtOffset(nextX + 5, y - rowHeight + 10);
        contentStream.showText("");
        contentStream.endText();

        float nextXForBorder = startX;
        for (int i = 0; i <= columnWidths.length; i++) {
            contentStream.moveTo(nextXForBorder, y);
            contentStream.lineTo(nextXForBorder, y - rowHeight);
            contentStream.stroke();
            if (i < columnWidths.length) {
                nextXForBorder += columnWidths[i];
            }
        }

        contentStream.moveTo(startX, y);
        contentStream.lineTo(startX + tableWidth, y);
        contentStream.stroke();
        contentStream.moveTo(startX, y - rowHeight);
        contentStream.lineTo(startX + tableWidth, y - rowHeight);
        contentStream.stroke();
    }

    public static void drawApprovedBy(PDPageContentStream contentStream, float endX, float y, String text) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(endX - 100, y - 100);
        contentStream.showText(text);
        contentStream.endText();
    }
}
