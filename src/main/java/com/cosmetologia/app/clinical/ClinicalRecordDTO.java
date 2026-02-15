package com.cosmetologia.app.clinical;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClinicalRecordDTO {
    private UUID id;
    
    @NotNull(message = "Paciente es requerido")
    private UUID patientId;
    
    private String patientName;
    
    // Datos personales
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private Integer age;
    private String occupation;
    private String address;
    private String emergencyContact;
    private String emergencyPhone;
    
    // Antecedentes médicos
    private String medicalHistory;
    private String allergies;
    private String currentMedications;
    private String chronicDiseases;
    private String previousSurgeries;
    
    // Antecedentes dermatológicos
    private String skinType;
    private String skinConditions;
    private String previousTreatments;
    private String sunExposure;
    private Boolean tanningBedUse;
    
    // Hábitos
    private Boolean smoking;
    private Boolean alcoholConsumption;
    private String exerciseFrequency;
    private String dietHabits;
    
    // Observaciones
    private String initialObservations;
    private String specialConsiderations;
    private String notes;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @NotNull(message = "Documento es requerido")
    private String documentId;
    private String medicalSociety;
    private String emergencyMedicalSociety;
    private String cosmeHabits;
    private List<String> pathologies;
    private String otherComments;
}
