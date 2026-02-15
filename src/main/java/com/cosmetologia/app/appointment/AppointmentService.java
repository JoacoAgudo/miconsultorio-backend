package com.cosmetologia.app.appointment;

import com.cosmetologia.app.patient.Patient;
import com.cosmetologia.app.patient.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAppointmentsInRange(LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findAppointmentsInRange(start, end).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAppointmentsByPatient(UUID patientId) {
        return appointmentRepository.findByPatientId(patientId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AppointmentDTO getAppointmentById(UUID id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado con id: " + id));
        return toDTO(appointment);
    }

    @Transactional
    public AppointmentDTO createAppointment(AppointmentDTO dto) {
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + dto.getPatientId()));
        
        Appointment appointment = Appointment.builder()
                .patient(patient)
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .treatmentType(dto.getTreatmentType())
                .status(dto.getStatus() != null ? dto.getStatus() : Appointment.AppointmentStatus.SCHEDULED)
                .notes(dto.getNotes())
                .build();
        
        Appointment saved = appointmentRepository.save(appointment);
        return toDTO(saved);
    }

    @Transactional
    public AppointmentDTO updateAppointment(UUID id, AppointmentDTO dto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado con id: " + id));
        
        if (dto.getPatientId() != null && !dto.getPatientId().equals(appointment.getPatient().getId())) {
            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + dto.getPatientId()));
            appointment.setPatient(patient);
        }
        
        appointment.setStartTime(dto.getStartTime());
        appointment.setEndTime(dto.getEndTime());
        appointment.setTreatmentType(dto.getTreatmentType());
        if (dto.getStatus() != null) {
            appointment.setStatus(dto.getStatus());
        }
        appointment.setNotes(dto.getNotes());
        
        Appointment updated = appointmentRepository.save(appointment);
        return toDTO(updated);
    }

    @Transactional
    public void deleteAppointment(UUID id) {
        if (!appointmentRepository.existsById(id)) {
            throw new RuntimeException("Turno no encontrado con id: " + id);
        }
        appointmentRepository.deleteById(id);
    }

    private AppointmentDTO toDTO(Appointment appointment) {
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .patientId(appointment.getPatient().getId())
                .patientName(appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName())
                .startTime(appointment.getStartTime())
                .endTime(appointment.getEndTime())
                .treatmentType(appointment.getTreatmentType())
                .status(appointment.getStatus())
                .notes(appointment.getNotes())
                .createdAt(appointment.getCreatedAt())
                .build();
    }
}
