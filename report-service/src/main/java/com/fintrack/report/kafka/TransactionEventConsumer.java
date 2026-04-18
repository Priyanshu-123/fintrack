package com.fintrack.report.kafka;


import com.fintrack.report.service.PdfReportGenerator;
import com.fintrack.report.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionEventConsumer {

    private final S3UploadService s3UploadService;
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
            String fileName = pdfPath.substring(pdfPath.lastIndexOf("/")+1);
            String s3Url = s3UploadService.uploadPdf(pdfPath, fileName);
            if (s3Url != null) {
                log.info("PDF report successfully uploaded to S3 at: {}", s3Url);
            }else {
                log.info("PDF report failed to upload to S3 at: {}", s3Url);
            }
        } else {
            log.error("Failed to generate PDF for event: {}", message);
        }
    }
}
