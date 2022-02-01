package com.cscb869.carserviceserver.data.repository;

import com.cscb869.carserviceserver.data.entity.Appointment;
import com.cscb869.carserviceserver.data.type.Status;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Appointment findAppointmentById(Long id);
    List<Appointment> findAppointmentsByCarServiceCompanyId(Long id);
    List<Appointment> findAppointmentsByCarServiceCompanyIdAndStatus(Long id, Status status);
    Appointment findAppointmentByDateAndStartTime(LocalDate date, LocalTime time);
    List<Appointment> findAppointmentsByDateAndCarServiceCompanyIdAndStatus(LocalDate date, Long id, Status status);
    List<Appointment> findAppointmentsByAccountIdAndStatus(Long id, Status status);
    List<Appointment> findAppointmentsByAccountIdAndCarServiceCompanyIdAndStatus(Long clientId, Long companyId, Status status);

}
