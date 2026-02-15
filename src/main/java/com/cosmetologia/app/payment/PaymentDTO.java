package com.cosmetologia.app.payment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private UUID id;
    
    @NotNull(message = "Paciente es requerido")
    private UUID patientId;
    
    private String patientName;
    
    @NotNull(message = "Monto es requerido")
    @Positive(message = "Monto debe ser positivo")
    private BigDecimal amount;
    
    @NotNull(message = "Método de pago es requerido")
    private Payment.PaymentMethod paymentMethod;
    
    @NotNull(message = "Fecha de pago es requerida")
    private LocalDateTime paidAt;
    
    private String notes;
    private LocalDateTime createdAt;
}
