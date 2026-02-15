package com.cosmetologia.app.treatment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TreatmentService {

    private final TreatmentRepository treatmentRepository;

    public List<TreatmentDTO> getAllTreatments() {
        return treatmentRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TreatmentDTO> getActiveTreatments() {
        return treatmentRepository.findByActiveTrueOrderByNameAsc().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TreatmentDTO> searchTreatments(String search) {
        return treatmentRepository.searchTreatments(search).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public TreatmentDTO getTreatmentById(UUID id) {
        Treatment treatment = treatmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado con id: " + id));
        return toDTO(treatment);
    }

    @Transactional
    public TreatmentDTO createTreatment(TreatmentDTO dto) {
        Treatment treatment = Treatment.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .durationMinutes(dto.getDurationMinutes())
                .notes(dto.getNotes())
                .active(dto.getActive() != null ? dto.getActive() : true)
                .build();
        
        Treatment saved = treatmentRepository.save(treatment);
        return toDTO(saved);
    }

    @Transactional
    public TreatmentDTO updateTreatment(UUID id, TreatmentDTO dto) {
        Treatment treatment = treatmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado con id: " + id));
        
        treatment.setName(dto.getName());
        treatment.setDescription(dto.getDescription());
        treatment.setPrice(dto.getPrice());
        treatment.setDurationMinutes(dto.getDurationMinutes());
        treatment.setNotes(dto.getNotes());
        if (dto.getActive() != null) {
            treatment.setActive(dto.getActive());
        }
        
        Treatment updated = treatmentRepository.save(treatment);
        return toDTO(updated);
    }

    @Transactional
    public void deleteTreatment(UUID id) {
        if (!treatmentRepository.existsById(id)) {
            throw new RuntimeException("Tratamiento no encontrado con id: " + id);
        }
        treatmentRepository.deleteById(id);
    }

    private TreatmentDTO toDTO(Treatment treatment) {
        return TreatmentDTO.builder()
                .id(treatment.getId())
                .name(treatment.getName())
                .description(treatment.getDescription())
                .price(treatment.getPrice())
                .durationMinutes(treatment.getDurationMinutes())
                .notes(treatment.getNotes())
                .active(treatment.getActive())
                .createdAt(treatment.getCreatedAt())
                .updatedAt(treatment.getUpdatedAt())
                .build();
    }
}
