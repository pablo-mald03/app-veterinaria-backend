package com.happypets.app_veterinaria_backend.payment.application.handler;

import com.happypets.app_veterinaria_backend.common.domain.exception.BusinessRuleException;
import com.happypets.app_veterinaria_backend.payment.application.command.ProcessPaymentCommand;
import com.happypets.app_veterinaria_backend.payment.domain.model.Payment;
import com.happypets.app_veterinaria_backend.payment.domain.port.PaymentRepositoryPort;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto.PaymentResponseDTO;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto.ProcessPaymentRequestDTO;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.mapper.PaymentDTOMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessPaymentHandlerTest {
    @Mock
    private PaymentRepositoryPort paymentRepositoryPort;

    @Mock
    private PaymentDTOMapper mapper;

    @InjectMocks
    private ProcessPaymentHandler handler;

    @Test
    void shouldChangeStatusToPaidAndSetPaymentDetails() {
        // Arrange
        Long paymentId = 1L;
        ProcessPaymentRequestDTO requestDTO = new ProcessPaymentRequestDTO();
        requestDTO.setPaymentMethod("CREDIT_CARD");

        ProcessPaymentCommand command = new ProcessPaymentCommand(paymentId, requestDTO);

        Payment existingPayment = new Payment();
        existingPayment.setId(paymentId);
        existingPayment.setStatus("PENDING");

        // Simular que el repositorio encuentra la cuenta
        when(paymentRepositoryPort.findById(paymentId)).thenReturn(Optional.of(existingPayment));
        // Simular que el guardado devuelve la misma instancia modificada
        when(paymentRepositoryPort.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PaymentResponseDTO mockResponse = new PaymentResponseDTO();
        mockResponse.setStatus("PAID");
        when(mapper.toDTO(any(Payment.class))).thenReturn(mockResponse);

        // Act
        PaymentResponseDTO result = handler.handle(command);

        // Assert
        assertEquals("PAID", existingPayment.getStatus());
        assertEquals("CREDIT_CARD", existingPayment.getPaymentMethod());
        assertNotNull(existingPayment.getPaymentDate());
        assertEquals("PAID", result.getStatus());

        // Verificar que el puerto de guardado se llamó exactamente una vez
        verify(paymentRepositoryPort, times(1)).save(existingPayment);
    }

    @Test
    void shouldThrowExceptionWhenPaymentIsAlreadyPaid() {
        // Arrange
        Long paymentId = 2L;
        ProcessPaymentRequestDTO requestDTO = new ProcessPaymentRequestDTO();
        requestDTO.setPaymentMethod("CASH");



        ProcessPaymentCommand command = new ProcessPaymentCommand(paymentId, requestDTO);

        Payment alreadyPaidPayment = new Payment();
        alreadyPaidPayment.setId(paymentId);
        alreadyPaidPayment.setStatus("PAID");

        when(paymentRepositoryPort.findById(paymentId)).thenReturn(Optional.of(alreadyPaidPayment));

        // Act & Assert: Verificar que lanza la excepción de regla de negocio
        BusinessRuleException exception = assertThrows(
                BusinessRuleException.class,
                () -> handler.handle(command)
        );

        assertTrue(exception.getMessage().contains("Este ticket ya se encuentra en estado PAID"));

        // Verificar que NUNCA se intentó guardar en la base de datos
        verify(paymentRepositoryPort, never()).save(any(Payment.class));
    }
}
