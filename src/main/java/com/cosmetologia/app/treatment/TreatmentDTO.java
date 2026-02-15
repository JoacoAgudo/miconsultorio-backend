package com.cosmetologia.app.treatment;

import jakarta.validation.constraints.NotBlank;
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
public class TreatmentDTO {
    private UUID id;
    
    @NotBlank(message = "Nombre es requerido")
    private String name;
    
    private String description;
    
    private BigDecimal price;
    
    @NotNull(message = "Duración es requerida")
    @Positive(message = "Duración debe ser positiva")
    private Integer durationMinutes;
    
    private String notes;
    
    @NotNull(message = "Estado activo es requerido")
    private Boolean active;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
