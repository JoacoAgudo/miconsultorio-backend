package com.cosmetologia.app.clinical;

import com.cosmetologia.app.patient.Patient;
import com.cosmetologia.app.patient.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ClinicalRecordService {

    private final ClinicalRecordRepository clinicalRecordRepository;
    private final PatientRepository patientRepository;

    public ClinicalRecordDTO getClinicalRecordByPatientId(UUID patientId) {
        ClinicalRecord record = clinicalRecordRepository.findByPatientId(patientId)
                .orElse(null);

        if (record == null) {
            return null;
        }

        return toDTO(record);
    }

    @Transactional
    public ClinicalRecordDTO createOrUpdateClinicalRecord(ClinicalRecordDTO dto) {
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + dto.getPatientId()));

        ClinicalRecord record = clinicalRecordRepository.findByPatientId(dto.getPatientId())
                .orElse(null);

        String pathologiesStr = dto.getPathologies() != null ? String.join(",", dto.getPathologies()) : null;

        if (record == null) {
            // Crear nuevo registro
            record = ClinicalRecord.builder()
                    .patient(patient)
                    .birthDate(dto.getBirthDate())
                    .age(dto.getAge())
                    .occupation(dto.getOccupation())
                    .address(dto.getAddress())
                    .emergencyContact(dto.getEmergencyContact())
                    .emergencyPhone(dto.getEmergencyPhone())
                    .medicalHistory(dto.getMedicalHistory())
                    .allergies(dto.getAllergies())
                    .currentMedications(dto.getCurrentMedications())
                    .chronicDiseases(dto.getChronicDiseases())
                    .previousSurgeries(dto.getPreviousSurgeries())
                    .skinType(dto.getSkinType())
                    .skinConditions(dto.getSkinConditions())
                    .previousTreatments(dto.getPreviousTreatments())
                    .sunExposure(dto.getSunExposure())
                    .tanningBedUse(dto.getTanningBedUse())
                    .smoking(dto.getSmoking())
                    .alcoholConsumption(dto.getAlcoholConsumption())
                    .exerciseFrequency(dto.getExerciseFrequency())
                    .dietHabits(dto.getDietHabits())
                    .initialObservations(dto.getInitialObservations())
                    .specialConsiderations(dto.getSpecialConsiderations())
                    .notes(dto.getNotes())
                    .documentId(dto.getDocumentId())
                    .medicalSociety(dto.getMedicalSociety())
                    .emergencyMedicalSociety(dto.getEmergencyMedicalSociety())
                    .cosmeHabits(dto.getCosmeHabits())
                    .pathologies(pathologiesStr)
                    .otherComments(dto.getOtherComments())
                    .build();
        } else {
            // Actualizar registro existente
            record.setBirthDate(dto.getBirthDate());
            record.setAge(dto.getAge());
            record.setOccupation(dto.getOccupation());
            record.setAddress(dto.getAddress());
            record.setEmergencyContact(dto.getEmergencyContact());
            record.setEmergencyPhone(dto.getEmergencyPhone());
            record.setMedicalHistory(dto.getMedicalHistory());
            record.setAllergies(dto.getAllergies());
            record.setCurrentMedications(dto.getCurrentMedications());
            record.setChronicDiseases(dto.getChronicDiseases());
            record.setPreviousSurgeries(dto.getPreviousSurgeries());
            record.setSkinType(dto.getSkinType());
            record.setSkinConditions(dto.getSkinConditions());
            record.setPreviousTreatments(dto.getPreviousTreatments());
            record.setSunExposure(dto.getSunExposure());
            record.setTanningBedUse(dto.getTanningBedUse());
            record.setSmoking(dto.getSmoking());
            record.setAlcoholConsumption(dto.getAlcoholConsumption());
            record.setExerciseFrequency(dto.getExerciseFrequency());
            record.setDietHabits(dto.getDietHabits());
            record.setInitialObservations(dto.getInitialObservations());
            record.setSpecialConsiderations(dto.getSpecialConsiderations());
            record.setNotes(dto.getNotes());
            record.setMedicalSociety(dto.getMedicalSociety());
            record.setEmergencyMedicalSociety(dto.getEmergencyMedicalSociety());
            record.setCosmeHabits(dto.getCosmeHabits());
            record.setDocumentId(dto.getDocumentId());
            record.setPathologies(pathologiesStr);
            record.setOtherComments(dto.getOtherComments());
        }

        ClinicalRecord saved = clinicalRecordRepository.save(record);
        return toDTO(saved);
    }

    @Transactional
    public void deleteClinicalRecord(UUID patientId) {
        ClinicalRecord record = clinicalRecordRepository.findByPatientId(patientId)
                .orElseThrow(() -> new RuntimeException("Ficha clínica no encontrada para el paciente: " + patientId));
        clinicalRecordRepository.delete(record);
    }

    private ClinicalRecordDTO toDTO(ClinicalRecord record) {
        List<String> pathologiesList = record.getPathologies() != null
                ? List.of(record.getPathologies().split(","))
                : List.of();

        return ClinicalRecordDTO.builder()
                .id(record.getId())
                .patientId(record.getPatient().getId())
                .patientName(record.getPatient().getFirstName() + " " + record.getPatient().getLastName())
                .birthDate(record.getBirthDate())
                .age(record.getAge())
                .occupation(record.getOccupation())
                .address(record.getAddress())
                .emergencyContact(record.getEmergencyContact())
                .emergencyPhone(record.getEmergencyPhone())
                .medicalHistory(record.getMedicalHistory())
                .allergies(record.getAllergies())
                .currentMedications(record.getCurrentMedications())
                .chronicDiseases(record.getChronicDiseases())
                .previousSurgeries(record.getPreviousSurgeries())
                .skinType(record.getSkinType())
                .skinConditions(record.getSkinConditions())
                .previousTreatments(record.getPreviousTreatments())
                .sunExposure(record.getSunExposure())
                .tanningBedUse(record.getTanningBedUse())
                .smoking(record.getSmoking())
                .alcoholConsumption(record.getAlcoholConsumption())
                .exerciseFrequency(record.getExerciseFrequency())
                .dietHabits(record.getDietHabits())
                .initialObservations(record.getInitialObservations())
                .specialConsiderations(record.getSpecialConsiderations())
                .notes(record.getNotes())
                .createdAt(record.getCreatedAt())
                .updatedAt(record.getUpdatedAt())
                .documentId(record.getDocumentId())
                .medicalSociety(record.getMedicalSociety())
                .emergencyMedicalSociety(record.getEmergencyMedicalSociety())
                .cosmeHabits(record.getCosmeHabits())
                .pathologies(pathologiesList)
                .otherComments(record.getOtherComments())
                .build();
    }
}