package com.cosmetologia.app.payment;

import com.cosmetologia.app.patient.Patient;
import com.cosmetologia.app.patient.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PatientRepository patientRepository;

    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PaymentDTO> getPaymentsInRange(LocalDateTime start, LocalDateTime end) {
        return paymentRepository.findPaymentsInRange(start, end).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PaymentDTO> getPaymentsByPatient(UUID patientId) {
        return paymentRepository.findByPatientId(patientId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public BigDecimal getTotalInRange(LocalDateTime start, LocalDateTime end) {
        BigDecimal total = paymentRepository.getTotalInRange(start, end);
        return total != null ? total : BigDecimal.ZERO;
    }

    public PaymentDTO getPaymentById(UUID id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con id: " + id));
        return toDTO(payment);
    }

    @Transactional
    public PaymentDTO createPayment(PaymentDTO dto) {
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + dto.getPatientId()));
        
        Payment payment = Payment.builder()
                .patient(patient)
                .amount(dto.getAmount())
                .paymentMethod(dto.getPaymentMethod())
                .paidAt(dto.getPaidAt() != null ? dto.getPaidAt() : LocalDateTime.now())
                .notes(dto.getNotes())
                .build();
        
        Payment saved = paymentRepository.save(payment);
        return toDTO(saved);
    }

    @Transactional
    public PaymentDTO updatePayment(UUID id, PaymentDTO dto) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con id: " + id));
        
        if (dto.getPatientId() != null && !dto.getPatientId().equals(payment.getPatient().getId())) {
            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + dto.getPatientId()));
            payment.setPatient(patient);
        }
        
        payment.setAmount(dto.getAmount());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setPaidAt(dto.getPaidAt());
        payment.setNotes(dto.getNotes());
        
        Payment updated = paymentRepository.save(payment);
        return toDTO(updated);
    }

    @Transactional
    public void deletePayment(UUID id) {
        if (!paymentRepository.existsById(id)) {
            throw new RuntimeException("Pago no encontrado con id: " + id);
        }
        paymentRepository.deleteById(id);
    }

    private PaymentDTO toDTO(Payment payment) {
        return PaymentDTO.builder()
                .id(payment.getId())
                .patientId(payment.getPatient().getId())
                .patientName(payment.getPatient().getFirstName() + " " + payment.getPatient().getLastName())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .paidAt(payment.getPaidAt())
                .notes(payment.getNotes())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}
