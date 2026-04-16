package com.fintrack.report.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class PdfReportGenerator {

    public String generateReport(String transactionEvent) {
        String fileName = "report_" + System.currentTimeMillis() + ".pdf";
        String filePath = System.getProperty("java.io.tmpdir") + "/" + fileName;

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font bodyFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            document.add(new Paragraph("FinTrack Transaction Report", titleFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Transaction Details:", bodyFont));
            document.add(new Paragraph(transactionEvent, bodyFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Generated at: " + new java.util.Date(), bodyFont));

            document.close();
            log.info("PDF report generated at: {}", filePath);
            return filePath;

        } catch (DocumentException | IOException e) {
            log.error("Failed to generate PDF report: {}", e.getMessage());
            return null;
        }
    }
}