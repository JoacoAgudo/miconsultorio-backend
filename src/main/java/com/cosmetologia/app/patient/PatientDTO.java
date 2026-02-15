package com.cosmetologia.app.patient;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class PatientDTO {
    private UUID id;
    
    @NotBlank(message = "Nombre es requerido")
    private String firstName;
    
    @NotBlank(message = "Apellido es requerido")
    private String lastName;
    
    private String phone;
    
    @Email(message = "Email debe ser válido")
    private String email;
    
    private String notes;
    private LocalDateTime createdAt;
}
