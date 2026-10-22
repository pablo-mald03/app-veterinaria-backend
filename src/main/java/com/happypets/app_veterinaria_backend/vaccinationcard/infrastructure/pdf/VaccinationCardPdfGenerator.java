package com.happypets.app_veterinaria_backend.vaccinationcard.infrastructure.pdf;

import com.happypets.app_veterinaria_backend.vaccinationcard.domain.model.VaccinationCardReport;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Genera el carnet de tratamientos de una mascota en PDF con OpenPDF.
 */
@Component
public class VaccinationCardPdfGenerator {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ISO_LOCAL_DATE;

    public byte[] generate(VaccinationCardReport report) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4.rotate(), 36, 36, 36, 36);

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            Font textFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 9);

            document.add(new Paragraph("Carnet de tratamientos", titleFont));
            document.add(new Paragraph(" "));

            PdfPTable info = new PdfPTable(4);
            info.setWidthPercentage(100);
            addInfoRow(info, "Mascota", report.petName(), labelFont, textFont);
            addInfoRow(info, "ID mascota", String.valueOf(report.idPet()), labelFont, textFont);
            addInfoRow(info, "Especie", report.species(), labelFont, textFont);
            addInfoRow(info, "Raza", report.breed(), labelFont, textFont);
            addInfoRow(info, "Edad", report.age() + " años", labelFont, textFont);
            addInfoRow(info, "Peso", report.weight() + " kg", labelFont, textFont);
            addInfoRow(info, "Carnet creado", formatDate(report.creationDate()), labelFont, textFont);
            addInfoRow(info, "ID carnet", String.valueOf(report.idCard()), labelFont, textFont);
            document.add(info);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Historial de aplicaciones", labelFont));
            document.add(new Paragraph(" "));

            if (report.entries().isEmpty()) {
                document.add(new Paragraph("Esta mascota aún no tiene tratamientos registrados.", textFont));
            } else {
                document.add(buildApplicationsTable(report.entries(), headerFont, cellFont));
            }

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total de aplicaciones registradas: " + report.entries().size(), cellFont));
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo generar el PDF del carnet de tratamientos", e);
        } finally {
            document.close();
        }

        return out.toByteArray();
    }

    private PdfPTable buildApplicationsTable(List<VaccinationCardReport.Entry> entries, Font headerFont, Font cellFont) {
        List<String> headers = List.of("Tratamiento", "Aplicación #", "Fecha de aplicación", "Próxima aplicación", "Costo", "Notas");
        PdfPTable table = new PdfPTable(headers.size());
        table.setWidthPercentage(100);

        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
            cell.setBackgroundColor(new Color(230, 230, 230));
            table.addCell(cell);
        }

        for (VaccinationCardReport.Entry e : entries) {
            table.addCell(new PdfPCell(new Phrase(nz(e.treatmentName()), cellFont)));
            table.addCell(new PdfPCell(new Phrase(
                    e.applicationNumber() == null ? "-" : String.valueOf(e.applicationNumber()), cellFont)));
            table.addCell(new PdfPCell(new Phrase(formatDate(e.applicationDate()), cellFont)));
            table.addCell(new PdfPCell(new Phrase(formatDate(e.nextDoseDate()), cellFont)));
            table.addCell(new PdfPCell(new Phrase(formatCost(e.totalCost()), cellFont)));
            table.addCell(new PdfPCell(new Phrase(nz(e.notes()), cellFont)));
        }
        return table;
    }

    private void addInfoRow(PdfPTable table, String label, String value, Font labelFont, Font textFont) {
        table.addCell(new PdfPCell(new Phrase(label, labelFont)));
        table.addCell(new PdfPCell(new Phrase(nz(value), textFont)));
    }

    private static String formatDate(LocalDate date) {
        return date == null ? "-" : date.format(DATE_FMT);
    }

    private static String formatCost(BigDecimal cost) {
        return cost == null ? "Q0.00" : String.format("Q%.2f", cost);
    }

    private static String nz(String value) {
        return value == null || value.isBlank() ? "-" : value;
    }
}