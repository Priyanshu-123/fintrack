package com.fintrack.report.kafka;


import com.fintrack.report.service.PdfReportGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionEventConsumer {

    private final PdfReportGenerator pdfReportGenerator;

    @KafkaListener(
            topics = "transactions.events",
            groupId = "report.group"
    )
    public void consumeTransactionEvents(String message){
        log.info("Report Service received event: {}", message);

        String pdfPath = pdfReportGenerator.generateReport(message);

        if (pdfPath != null) {
            log.info("PDF report successfully created at: {}", pdfPath);
            // TODO: Upload PDF to AWS S3
        } else {
            log.error("Failed to generate PDF for event: {}", message);
        }
    }
}
