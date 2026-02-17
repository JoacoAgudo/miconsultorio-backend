package com.cosmetologia.app.clinical;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clinical-sessions")
@RequiredArgsConstructor
public class ClinicalSessionController {

    private final ClinicalSessionService clinicalSessionService;

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<ClinicalSessionDTO>> getSessionsByPatientId(@PathVariable UUID patientId) {
        List<ClinicalSessionDTO> sessions = clinicalSessionService.getSessionsByPatientId(patientId);
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClinicalSessionDTO> getSessionById(@PathVariable UUID id) {
        return ResponseEntity.ok(clinicalSessionService.getSessionById(id));
    }

    @PostMapping
    public ResponseEntity<ClinicalSessionDTO> createSession(@Valid @RequestBody ClinicalSessionDTO dto) {
        ClinicalSessionDTO created = clinicalSessionService.createSession(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClinicalSessionDTO> updateSession(
            @PathVariable UUID id,
            @Valid @RequestBody ClinicalSessionDTO dto) {
        ClinicalSessionDTO updated = clinicalSessionService.updateSession(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable UUID id) {
        clinicalSessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}
