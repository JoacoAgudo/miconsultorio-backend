package com.cosmetologia.app.clinical;

import com.cosmetologia.app.patient.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "clinical_records")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClinicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "patient_id", nullable = false, unique = true)
    private Patient patient;

    // Datos personales y médicos
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "age")
    private Integer age;

    @Column(name = "occupation", length = 255)
    private String occupation;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "emergency_contact", length = 255)
    private String emergencyContact;

    @Column(name = "emergency_phone", length = 50)
    private String emergencyPhone;

    // Antecedentes médicos
    @Column(name = "medical_history", columnDefinition = "TEXT")
    private String medicalHistory;

    @Column(name = "allergies", columnDefinition = "TEXT")
    private String allergies;

    @Column(name = "current_medications", columnDefinition = "TEXT")
    private String currentMedications;

    @Column(name = "chronic_diseases", columnDefinition = "TEXT")
    private String chronicDiseases;

    @Column(name = "previous_surgeries", columnDefinition = "TEXT")
    private String previousSurgeries;

    // Antecedentes dermatológicos
    @Column(name = "skin_type", length = 50)
    private String skinType; // Normal, Seca, Grasa, Mixta, Sensible

    @Column(name = "skin_conditions", columnDefinition = "TEXT")
    private String skinConditions;

    @Column(name = "previous_treatments", columnDefinition = "TEXT")
    private String previousTreatments;

    @Column(name = "sun_exposure", length = 100)
    private String sunExposure;

    @Column(name = "tanning_bed_use")
    private Boolean tanningBedUse;

    // Hábitos
    @Column(name = "smoking")
    private Boolean smoking;

    @Column(name = "alcohol_consumption")
    private Boolean alcoholConsumption;

    @Column(name = "exercise_frequency", length = 100)
    private String exerciseFrequency;

    @Column(name = "diet_habits", columnDefinition = "TEXT")
    private String dietHabits;

    // Observaciones y notas
    @Column(name = "initial_observations", columnDefinition = "TEXT")
    private String initialObservations;

    @Column(name = "special_considerations", columnDefinition = "TEXT")
    private String specialConsiderations;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "document_id", length = 20)
    private String documentId;

    @Column(name = "medicalSociety", length = 20)
    private String medicalSociety;

    @Column(name = "emergency", length = 20)
    private String emergencyMedicalSociety;

    @Column(name = "cosme_habits", columnDefinition = "TEXT")
    private String cosmeHabits;

    @Column(name = "pathologies", columnDefinition = "TEXT")
    private String pathologies; // Guardamos "Asma,Alergias,Tiroides" como una cadena de texto

    @Column(name = "other_comments", columnDefinition = "TEXT")
    private String otherComments;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
