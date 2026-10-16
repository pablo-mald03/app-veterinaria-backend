package com.happypets.app_veterinaria_backend.payment.domain.port;

import com.happypets.app_veterinaria_backend.payment.domain.model.Payment;

public interface InvoiceGeneratorPort {
    byte[] generatePdf(Payment payment);
}
