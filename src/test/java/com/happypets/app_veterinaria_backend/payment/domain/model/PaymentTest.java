package com.happypets.app_veterinaria_backend.payment.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentTest {
    @Test
    void shouldCalculateTotalAmountWhenMultipleItemsAreAdded() {
        // Arrange: Preparar la cuenta
        Payment payment = new Payment();
        payment.setTotalAmount(BigDecimal.ZERO);

        // Crear Ítem 1 (Consulta: 1 x 150.00 = 150.00)
        PaymentItem consultation = new PaymentItem();
        consultation.setDescription("Consulta");
        consultation.setUnitPrice(new BigDecimal("150.00"));
        consultation.setQuantity(1);

        // Crear Ítem 2 (Vacuna: 2 x 75.00 = 150.00)
        PaymentItem vaccines = new PaymentItem();
        vaccines.setDescription("Vacunas");
        vaccines.setUnitPrice(new BigDecimal("75.00"));
        vaccines.setQuantity(2);

        // Act: Ejecutar la lógica de negocio (el metodo addItem calcula el subtotal internamente)
        payment.addItem(consultation);
        payment.addItem(vaccines);

        // Assert: Verificar que el total es la suma exacta de los subtotales (300.00)
        assertEquals(new BigDecimal("300.00"), payment.getTotalAmount());
        assertEquals(new BigDecimal("150.00"), payment.getItems().get(0).getSubtotal());
        assertEquals(new BigDecimal("150.00"), payment.getItems().get(1).getSubtotal());
    }
}
