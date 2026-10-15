package com.happypets.app_veterinaria_backend.payment.application.handler;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.common.domain.exception.BusinessRuleException;
import com.happypets.app_veterinaria_backend.common.domain.exception.ResourceNotFoundException;
import com.happypets.app_veterinaria_backend.payment.application.query.DownloadInvoiceQuery;
import com.happypets.app_veterinaria_backend.payment.domain.model.Payment;
import com.happypets.app_veterinaria_backend.payment.domain.port.InvoiceGeneratorPort;
import com.happypets.app_veterinaria_backend.payment.domain.port.PaymentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DownloadInvoiceHandler implements RequestHandler<DownloadInvoiceQuery, byte[]> {
    private final PaymentRepositoryPort paymentRepositoryPort;
    private final InvoiceGeneratorPort invoiceGeneratorPort;

    @Override
    public byte[] handle(DownloadInvoiceQuery query) {
        Payment payment = paymentRepositoryPort.findById(query.getPaymentId())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada.") {});

        // Opcional: Validar que solo se impriman facturas pagadas
        if (!"PAID".equals(payment.getStatus())) {
            throw new BusinessRuleException("No se puede generar factura de una cuenta no pagada.") {};
        }

        return invoiceGeneratorPort.generatePdf(payment);
    }

    @Override
    public Class<DownloadInvoiceQuery> getRequestType() {
        return DownloadInvoiceQuery.class;
    }
}
