package com.cosmetologia.app.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    
    List<Appointment> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT a FROM Appointment a WHERE a.patient.id = :patientId ORDER BY a.startTime DESC")
    List<Appointment> findByPatientId(@Param("patientId") UUID patientId);
    
    @Query("SELECT a FROM Appointment a WHERE a.startTime >= :start AND a.startTime < :end ORDER BY a.startTime ASC")
    List<Appointment> findAppointmentsInRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
