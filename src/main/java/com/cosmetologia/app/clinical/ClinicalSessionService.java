package com.cosmetologia.app.clinical;

import com.cosmetologia.app.patient.Patient;
import com.cosmetologia.app.patient.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClinicalSessionService {

    private final ClinicalSessionRepository clinicalSessionRepository;
    private final PatientRepository patientRepository;

    public List<ClinicalSessionDTO> getSessionsByPatientId(UUID patientId) {
        return clinicalSessionRepository.findByPatientIdOrderBySessionDateDesc(patientId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ClinicalSessionDTO getSessionById(UUID id) {
        ClinicalSession session = clinicalSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada con id: " + id));
        return toDTO(session);
    }

    @Transactional
    public ClinicalSessionDTO createSession(ClinicalSessionDTO dto) {
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + dto.getPatientId()));

        // Calcular el número de sesión si no se proporciona
        Integer sessionNumber = dto.getSessionNumber();
        if (sessionNumber == null) {
            sessionNumber = clinicalSessionRepository.findMaxSessionNumberByPatientId(patient.getId())
                    .map(max -> max + 1)
                    .orElse(1);
        }

        ClinicalSession session = ClinicalSession.builder()
                .patient(patient)
                .sessionDate(dto.getSessionDate())
                .sessionNumber(sessionNumber)
                .treatmentType(dto.getTreatmentType())
                .treatmentDescription(dto.getTreatmentDescription())
                .productsUsed(dto.getProductsUsed())
                .techniquesApplied(dto.getTechniquesApplied())
                .skinConditionBefore(dto.getSkinConditionBefore())
                .skinConditionAfter(dto.getSkinConditionAfter())
                .reactionsObserved(dto.getReactionsObserved())
                .patientFeedback(dto.getPatientFeedback())
                .homeCareRecommendations(dto.getHomeCareRecommendations())
                .nextSessionRecommendations(dto.getNextSessionRecommendations())
                .productsRecommended(dto.getProductsRecommended())
                .notes(dto.getNotes())
                .build();

        ClinicalSession saved = clinicalSessionRepository.save(session);
        return toDTO(saved);
    }

    @Transactional
    public ClinicalSessionDTO updateSession(UUID id, ClinicalSessionDTO dto) {
        ClinicalSession session = clinicalSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada con id: " + id));

        session.setSessionDate(dto.getSessionDate());
        session.setSessionNumber(dto.getSessionNumber());
        session.setTreatmentType(dto.getTreatmentType());
        session.setTreatmentDescription(dto.getTreatmentDescription());
        session.setProductsUsed(dto.getProductsUsed());
        session.setTechniquesApplied(dto.getTechniquesApplied());
        session.setSkinConditionBefore(dto.getSkinConditionBefore());
        session.setSkinConditionAfter(dto.getSkinConditionAfter());
        session.setReactionsObserved(dto.getReactionsObserved());
        session.setPatientFeedback(dto.getPatientFeedback());
        session.setHomeCareRecommendations(dto.getHomeCareRecommendations());
        session.setNextSessionRecommendations(dto.getNextSessionRecommendations());
        session.setProductsRecommended(dto.getProductsRecommended());
        session.setNotes(dto.getNotes());

        ClinicalSession updated = clinicalSessionRepository.save(session);
        return toDTO(updated);
    }

    @Transactional
    public void deleteSession(UUID id) {
        if (!clinicalSessionRepository.existsById(id)) {
            throw new RuntimeException("Sesión no encontrada con id: " + id);
        }
        clinicalSessionRepository.deleteById(id);
    }

    private ClinicalSessionDTO toDTO(ClinicalSession session) {
        return ClinicalSessionDTO.builder()
                .id(session.getId())
                .patientId(session.getPatient().getId())
                .patientName(session.getPatient().getFirstName() + " " + session.getPatient().getLastName())
                .sessionDate(session.getSessionDate())
                .sessionNumber(session.getSessionNumber())
                .treatmentType(session.getTreatmentType())
                .treatmentDescription(session.getTreatmentDescription())
                .productsUsed(session.getProductsUsed())
                .techniquesApplied(session.getTechniquesApplied())
                .skinConditionBefore(session.getSkinConditionBefore())
                .skinConditionAfter(session.getSkinConditionAfter())
                .reactionsObserved(session.getReactionsObserved())
                .patientFeedback(session.getPatientFeedback())
                .homeCareRecommendations(session.getHomeCareRecommendations())
                .nextSessionRecommendations(session.getNextSessionRecommendations())
                .productsRecommended(session.getProductsRecommended())
                .notes(session.getNotes())
                .createdAt(session.getCreatedAt())
                .updatedAt(session.getUpdatedAt())
                .build();
    }
}
