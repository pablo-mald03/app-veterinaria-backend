package com.happypets.app_veterinaria_backend.payment.infrastructure.document;

import com.happypets.app_veterinaria_backend.payment.domain.model.Payment;
import com.happypets.app_veterinaria_backend.payment.domain.model.PaymentItem;
import com.happypets.app_veterinaria_backend.payment.domain.port.InvoiceGeneratorPort;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Component
public class OpenPdfInvoiceAdapter implements InvoiceGeneratorPort {
    @Override
    public byte[] generatePdf(Payment payment) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            // 1. Encabezado de la Veterinaria
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
            Paragraph title = new Paragraph("VETERINARIA HAPPY PETS", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            // 2. Datos de la Factura
            Font textFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            document.add(new Paragraph("Ticket Nº: 000" + payment.getId(), textFont));
            document.add(new Paragraph("Fecha: " + payment.getPaymentDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), textFont));
            document.add(new Paragraph("Método de Pago: " + payment.getPaymentMethod(), textFont));
            document.add(new Paragraph("Estado: " + payment.getStatus(), textFont));
            document.add(new Paragraph("\n"));

            // 3. Tabla de Detalles (4 columnas)
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{4f, 1f, 2f, 2f});

            // Cabeceras de la tabla
            addTableHeader(table, "Descripción");
            addTableHeader(table, "Cant.");
            addTableHeader(table, "Precio Unit.");
            addTableHeader(table, "Subtotal");

            // Llenar datos de los ítems
            for (PaymentItem item : payment.getItems()) {
                table.addCell(new Paragraph(item.getDescription(), textFont));
                table.addCell(new Paragraph(String.valueOf(item.getQuantity()), textFont));
                table.addCell(new Paragraph("Q " + item.getUnitPrice().toString(), textFont));
                table.addCell(new Paragraph("Q " + item.getSubtotal().toString(), textFont));
            }
            document.add(table);
            document.add(new Paragraph("\n"));

            // 4. Total a Pagar
            Font totalFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Paragraph total = new Paragraph("TOTAL A PAGAR: Q " + payment.getTotalAmount().toString(), totalFont);
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar la factura en PDF", e);
        }
    }

    private void addTableHeader(PdfPTable table, String title) {
        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);
        cell.setPhrase(new Phrase(title, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
        table.addCell(cell);
    }
}
