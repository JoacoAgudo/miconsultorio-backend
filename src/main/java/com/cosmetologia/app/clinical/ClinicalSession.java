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
@Table(name = "clinical_sessions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClinicalSession {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "session_date", nullable = false)
    private LocalDate sessionDate;

    @Column(name = "session_number")
    private Integer sessionNumber; // Número de sesión para este paciente

    // Tratamiento realizado
    @Column(name = "treatment_type", length = 255)
    private String treatmentType;

    @Column(name = "treatment_description", columnDefinition = "TEXT")
    private String treatmentDescription;

    @Column(name = "products_used", columnDefinition = "TEXT")
    private String productsUsed;

    @Column(name = "techniques_applied", columnDefinition = "TEXT")
    private String techniquesApplied;

    // Evaluación
    @Column(name = "skin_condition_before", columnDefinition = "TEXT")
    private String skinConditionBefore;

    @Column(name = "skin_condition_after", columnDefinition = "TEXT")
    private String skinConditionAfter;

    @Column(name = "reactions_observed", columnDefinition = "TEXT")
    private String reactionsObserved;

    @Column(name = "patient_feedback", columnDefinition = "TEXT")
    private String patientFeedback;

    // Recomendaciones
    @Column(name = "home_care_recommendations", columnDefinition = "TEXT")
    private String homeCareRecommendations;

    @Column(name = "next_session_recommendations", columnDefinition = "TEXT")
    private String nextSessionRecommendations;

    @Column(name = "products_recommended", columnDefinition = "TEXT")
    private String productsRecommended;

    // Notas adicionales
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

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
