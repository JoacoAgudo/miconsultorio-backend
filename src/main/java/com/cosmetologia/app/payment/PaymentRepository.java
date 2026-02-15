package com.cosmetologia.app.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    
    @Query("SELECT p FROM Payment p WHERE p.patient.id = :patientId ORDER BY p.paidAt DESC")
    List<Payment> findByPatientId(@Param("patientId") UUID patientId);
    
    @Query("SELECT p FROM Payment p WHERE p.paidAt >= :start AND p.paidAt < :end ORDER BY p.paidAt DESC")
    List<Payment> findPaymentsInRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.paidAt >= :start AND p.paidAt < :end")
    BigDecimal getTotalInRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
