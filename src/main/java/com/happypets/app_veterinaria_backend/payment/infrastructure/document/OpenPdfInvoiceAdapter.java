package com.happypets.app_veterinaria_backend.payment.infrastructure.document;

import com.happypets.app_veterinaria_backend.payment.domain.model.Payment;
import com.happypets.app_veterinaria_backend.payment.domain.model.PaymentItem;
import com.happypets.app_veterinaria_backend.payment.domain.port.InvoiceGeneratorPort;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Component
public class OpenPdfInvoiceAdapter implements InvoiceGeneratorPort {
    @Override
    public byte[] generatePdf(Payment payment) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            // Documento con márgenes para un aspecto más limpio
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, out);
            document.open();

            // Paleta de Colores
            Color primaryColor = new Color(41, 128, 185); // Azul corporativo
            Color lightGray = new Color(240, 240, 240);
            Color darkGray = new Color(70, 70, 70);

            // --- 1. ENCABEZADO A DOS COLUMNAS ---
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new float[]{1f, 1f});

            // Columna Izquierda: Info de la Clínica
            PdfPCell companyCell = new PdfPCell();
            companyCell.setBorder(Rectangle.NO_BORDER);
            companyCell.addElement(new Paragraph("HAPPY PETS", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, primaryColor)));
            companyCell.addElement(new Paragraph("Clínica Veterinaria", FontFactory.getFont(FontFactory.HELVETICA, 12, darkGray)));
            companyCell.addElement(new Paragraph("Tel: +502 1234-5678", FontFactory.getFont(FontFactory.HELVETICA, 10, Color.GRAY)));
            headerTable.addCell(companyCell);

            // Columna Derecha: Detalles de Factura
            PdfPCell infoCell = new PdfPCell();
            infoCell.setBorder(Rectangle.NO_BORDER);
            Paragraph invoiceTitle = new Paragraph("FACTURA", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, darkGray));
            invoiceTitle.setAlignment(Element.ALIGN_RIGHT);
            infoCell.addElement(invoiceTitle);

            Paragraph invoiceDetails = new Paragraph(
                    "Ticket Nº: 000" + payment.getId() + "\n" +
                            "Fecha: " + payment.getPaymentDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n" +
                            "Estado: " + payment.getStatus(),
                    FontFactory.getFont(FontFactory.HELVETICA, 11, darkGray)
            );
            invoiceDetails.setAlignment(Element.ALIGN_RIGHT);
            infoCell.addElement(invoiceDetails);
            headerTable.addCell(infoCell);

            document.add(headerTable);

            // --- 2. LÍNEA SEPARADORA ---
            document.add(new Paragraph("\n"));
            LineSeparator ls = new LineSeparator();
            ls.setLineColor(lightGray);
            document.add(new Chunk(ls));
            document.add(new Paragraph("\n"));

            // --- 3. DETALLES DE PAGO ---
            PdfPTable subHeaderTable = new PdfPTable(1);
            subHeaderTable.setWidthPercentage(100);
            PdfPCell subCell = new PdfPCell();
            subCell.setBorder(Rectangle.NO_BORDER);
            subCell.addElement(new Paragraph("Detalles de Transacción:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, primaryColor)));
            subCell.addElement(new Paragraph("Método utilizado: " + payment.getPaymentMethod(), FontFactory.getFont(FontFactory.HELVETICA, 11, darkGray)));
            subHeaderTable.addCell(subCell);
            document.add(subHeaderTable);
            document.add(new Paragraph("\n"));

            // --- 4. TABLA PRINCIPAL DE ÍTEMS ---
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{4f, 1f, 2f, 2f});

            // Añadir cabeceras con diseño
            addTableHeader(table, "Descripción", primaryColor);
            addTableHeader(table, "Cant.", primaryColor);
            addTableHeader(table, "Precio Unit.", primaryColor);
            addTableHeader(table, "Subtotal", primaryColor);

            // Añadir filas de la factura
            Font rowFont = FontFactory.getFont(FontFactory.HELVETICA, 11, darkGray);
            for (PaymentItem item : payment.getItems()) {
                addCell(table, item.getDescription(), rowFont, Element.ALIGN_LEFT);
                addCell(table, String.valueOf(item.getQuantity()), rowFont, Element.ALIGN_CENTER);
                addCell(table, "Q " + item.getUnitPrice().toString(), rowFont, Element.ALIGN_RIGHT);
                addCell(table, "Q " + item.getSubtotal().toString(), rowFont, Element.ALIGN_RIGHT);
            }
            document.add(table);
            document.add(new Paragraph("\n"));

            // --- 5. BLOQUE DEL TOTAL ---
            PdfPTable totalTable = new PdfPTable(2);
            totalTable.setWidthPercentage(100);
            totalTable.setWidths(new float[]{7f, 3f}); // Empuja el total hacia la derecha

            // Celda vacía a la izquierda
            PdfPCell emptyCell = new PdfPCell(new Paragraph(""));
            emptyCell.setBorder(Rectangle.NO_BORDER);
            totalTable.addCell(emptyCell);

            // Celda del Total con marco y fondo
            PdfPCell totalCell = new PdfPCell();
            totalCell.setBackgroundColor(lightGray);
            totalCell.setPadding(10);
            totalCell.setBorderColor(primaryColor);
            totalCell.setBorderWidth(1.5f);
            Paragraph totalText = new Paragraph("TOTAL: Q " + payment.getTotalAmount().toString(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, primaryColor));
            totalText.setAlignment(Element.ALIGN_RIGHT);
            totalCell.addElement(totalText);
            totalTable.addCell(totalCell);

            document.add(totalTable);

            // --- 6. PIE DE PÁGINA ---
            document.add(new Paragraph("\n\n\n"));
            Paragraph footer = new Paragraph("¡Gracias por confiar en Happy Pets para el cuidado de tu mascota!", FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, Color.GRAY));
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar la factura en PDF", e);
        }
    }

    // Metodo auxiliar para pintar las cabeceras de la tabla
    private void addTableHeader(PdfPTable table, String title, Color bgColor) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(bgColor);
        cell.setPadding(8);
        cell.setBorderColor(Color.WHITE);
        cell.setPhrase(new Phrase(title, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }

    // Metodo auxiliar para pintar las celdas normales de la tabla
    private void addCell(PdfPTable table, String text, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(8);
        cell.setBorderColor(new Color(220, 220, 220)); // Borde gris muy claro
        cell.setHorizontalAlignment(alignment);
        table.addCell(cell);
    }
}
