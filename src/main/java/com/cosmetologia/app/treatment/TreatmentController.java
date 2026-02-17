package com.cosmetologia.app.treatment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/treatments")
@RequiredArgsConstructor
public class TreatmentController {

    private final TreatmentService treatmentService;

    @GetMapping
    public ResponseEntity<List<TreatmentDTO>> getAllTreatments(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean active) {
        List<TreatmentDTO> treatments;
        
        if (active != null && active) {
            treatments = treatmentService.getActiveTreatments();
        } else if (search != null && !search.trim().isEmpty()) {
            treatments = treatmentService.searchTreatments(search);
        } else {
            treatments = treatmentService.getAllTreatments();
        }
        
        return ResponseEntity.ok(treatments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreatmentDTO> getTreatmentById(@PathVariable UUID id) {
        return ResponseEntity.ok(treatmentService.getTreatmentById(id));
    }

    @PostMapping
    public ResponseEntity<TreatmentDTO> createTreatment(@Valid @RequestBody TreatmentDTO dto) {
        TreatmentDTO created = treatmentService.createTreatment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TreatmentDTO> updateTreatment(
            @PathVariable UUID id,
            @Valid @RequestBody TreatmentDTO dto) {
        TreatmentDTO updated = treatmentService.updateTreatment(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTreatment(@PathVariable UUID id) {
        treatmentService.deleteTreatment(id);
        return ResponseEntity.noContent().build();
    }
}
