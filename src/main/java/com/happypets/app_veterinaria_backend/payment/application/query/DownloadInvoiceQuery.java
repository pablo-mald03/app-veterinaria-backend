package com.happypets.app_veterinaria_backend.payment.application.query;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DownloadInvoiceQuery implements Request<byte[]> {
    private final Long paymentId;
}
