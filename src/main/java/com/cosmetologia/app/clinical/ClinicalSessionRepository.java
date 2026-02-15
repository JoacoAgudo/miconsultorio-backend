package com.cosmetologia.app.clinical;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClinicalSessionRepository extends JpaRepository<ClinicalSession, UUID> {
    List<ClinicalSession> findByPatientIdOrderBySessionDateDesc(UUID patientId);
    
    @Query("SELECT MAX(s.sessionNumber) FROM ClinicalSession s WHERE s.patient.id = :patientId")
    Optional<Integer> findMaxSessionNumberByPatientId(@Param("patientId") UUID patientId);
    
    List<ClinicalSession> findByPatientIdOrderBySessionDateAscSessionNumberAsc(UUID patientId);
}
