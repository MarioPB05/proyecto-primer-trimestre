package safa.safepaws.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class PdfService {

    private final TemplateEngine templateEngine;

    public byte[] generatePdf(String templateName, Context context) throws Exception {
        String htmlContent = templateEngine.process(templateName, context);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(htmlContent, null);
            builder.toStream(outputStream);
            builder.run();
            return outputStream.toByteArray();
        }
    }

    public String generatePdfAsBase64(String templateName, Context context) throws Exception {
        String htmlContent = templateEngine.process(templateName, context);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(htmlContent, null);
            builder.toStream(outputStream);
            builder.run();

            byte[] pdfBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(pdfBytes);
        }
    }

    public byte[] decodeBase64ToPdf(String base64Pdf) {
        return Base64.getDecoder().decode(base64Pdf);
    }

    public String generateSha256FromBase64(String base64Data) throws NoSuchAlgorithmException {
        byte[] documentBytes = Base64.getDecoder().decode(base64Data);

        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        byte[] hashBytes = digest.digest(documentBytes);

        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

}
