package com.cosmetologia.app.appointment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {
    private UUID id;
    
    @NotNull(message = "Paciente es requerido")
    private UUID patientId;
    
    private String patientName;
    
    @NotNull(message = "Fecha y hora de inicio es requerida")
    private LocalDateTime startTime;
    
    private LocalDateTime endTime;
    
    private String treatmentType;
    private Appointment.AppointmentStatus status;
    private String notes;
    private LocalDateTime createdAt;
}
