package com.happypets.app_veterinaria_backend.report.infrastructure.pdf;

import com.happypets.app_veterinaria_backend.report.domain.model.AttendedPetRecord;
import com.happypets.app_veterinaria_backend.report.domain.model.PendingTreatmentRecord;
import com.happypets.app_veterinaria_backend.report.domain.model.PendingVaccineRecord;
import com.happypets.app_veterinaria_backend.report.domain.model.TreatmentFrequency;
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

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

/*GENERADOR DE PDF*/
@Component
public class PdfReportGenerator {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ISO_LOCAL_DATE;

    public byte[] attendedPets(List<AttendedPetRecord> rows) {
        List<String> headers = List.of("Fecha", "Mascota", "Especie", "Diagnóstico", "Tratamiento", "Costo");
        List<List<String>> body = rows.stream()
                .map(r -> List.of(
                        r.date() == null ? "-" : r.date().format(DATE_FMT),
                        nz(r.petName()),
                        nz(r.species()),
                        nz(r.diagnosis()),
                        nz(r.treatment()),
                        r.cost() == null ? "-" : r.cost().toPlainString()))
                .toList();
        return build("Reporte de mascotas atendidas", headers, body);
    }

    public byte[] frequentTreatments(List<TreatmentFrequency> rows) {
        List<String> headers = List.of("Tratamiento", "Veces aplicado", "Costo total");
        List<List<String>> body = rows.stream()
                .map(r -> List.of(
                        nz(r.treatment()),
                        String.valueOf(r.timesApplied()),
                        r.totalCost() == null ? "-" : r.totalCost().toPlainString()))
                .toList();
        return build("Reporte de tratamientos frecuentes", headers, body);
    }

    public byte[] pendingTreatments(List<PendingTreatmentRecord> rows) {
        List<String> headers = List.of("Próxima aplicación", "Mascota", "Especie", "Tratamiento", "Última aplicación #");
        List<List<String>> body = rows.stream()
                .map(r -> List.of(
                        r.nextDoseDate() == null ? "-" : r.nextDoseDate().format(DATE_FMT),
                        nz(r.petName()),
                        nz(r.species()),
                        nz(r.treatmentName()),
                        r.applicationNumber() == null ? "-" : String.valueOf(r.applicationNumber())))
                .toList();
        return build("Reporte de tratamientos pendientes", headers, body);
    }

    private byte[] build(String title, List<String> headers, List<List<String>> rows) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4.rotate(), 36, 36, 36, 36);
        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 9);

            document.add(new Paragraph(title, titleFont));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(headers.size());
            table.setWidthPercentage(100);

            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(new java.awt.Color(230, 230, 230));
                table.addCell(cell);
            }
            for (List<String> row : rows) {
                for (String value : row) {
                    table.addCell(new PdfPCell(new Phrase(value, cellFont)));
                }
            }

            document.add(table);
            document.add(new Paragraph("Total de registros: " + rows.size(), cellFont));
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo generar el PDF del reporte", e);
        } finally {
            document.close();
        }
        return out.toByteArray();
    }

    private static String nz(String value) {
        return value == null || value.isBlank() ? "-" : value;
    }
}
