package com.cosmetologia.app.clinical;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/clinical-records")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class ClinicalRecordController {

    private final ClinicalRecordService clinicalRecordService;

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ClinicalRecordDTO> getClinicalRecordByPatientId(@PathVariable UUID patientId) {
        ClinicalRecordDTO record = clinicalRecordService.getClinicalRecordByPatientId(patientId);
        if (record == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(record);
    }

    @PostMapping
    public ResponseEntity<ClinicalRecordDTO> createOrUpdateClinicalRecord(@Valid @RequestBody ClinicalRecordDTO dto) {
        ClinicalRecordDTO saved = clinicalRecordService.createOrUpdateClinicalRecord(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/patient/{patientId}")
    public ResponseEntity<ClinicalRecordDTO> updateClinicalRecord(
            @PathVariable UUID patientId,
            @Valid @RequestBody ClinicalRecordDTO dto) {
        dto.setPatientId(patientId);
        ClinicalRecordDTO updated = clinicalRecordService.createOrUpdateClinicalRecord(dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/patient/{patientId}")
    public ResponseEntity<Void> deleteClinicalRecord(@PathVariable UUID patientId) {
        clinicalRecordService.deleteClinicalRecord(patientId);
        return ResponseEntity.noContent().build();
    }
}
