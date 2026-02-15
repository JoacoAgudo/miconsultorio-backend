package com.cosmetologia.app.clinical;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClinicalRecordRepository extends JpaRepository<ClinicalRecord, UUID> {
    Optional<ClinicalRecord> findByPatientId(UUID patientId);
    boolean existsByPatientId(UUID patientId);
}
