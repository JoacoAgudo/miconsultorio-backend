package com.cosmetologia.app.clinical;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClinicalSessionDTO {
    private UUID id;
    
    @NotNull(message = "Paciente es requerido")
    private UUID patientId;
    
    private String patientName;
    
    @NotNull(message = "Fecha de sesión es requerida")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate sessionDate;
    
    private Integer sessionNumber;
    
    // Tratamiento
    private String treatmentType;
    private String treatmentDescription;
    private String productsUsed;
    private String techniquesApplied;
    
    // Evaluación
    private String skinConditionBefore;
    private String skinConditionAfter;
    private String reactionsObserved;
    private String patientFeedback;
    
    // Recomendaciones
    private String homeCareRecommendations;
    private String nextSessionRecommendations;
    private String productsRecommended;
    
    // Notas
    private String notes;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
