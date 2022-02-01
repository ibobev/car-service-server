package com.cscb869.carserviceserver.services;

import com.cscb869.carserviceserver.data.entity.Appointment;
import com.cscb869.carserviceserver.data.type.Status;
import com.cscb869.carserviceserver.dto.AppointmentCompleteDTO;
import com.cscb869.carserviceserver.dto.AppointmentCreateDTO;
import com.cscb869.carserviceserver.dto.AppointmentDistinctAccountsDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentService {

    Appointment saveAppointment(AppointmentCreateDTO appointmentCreateDTO);
    Appointment getAppointment(Long id);
    List<Appointment> getAppointmentsByCompanyAndStatus(Long companyId, Status status);
    List<Appointment> getAllAppointmentsByCompanyAndStatusPending(Long companyId);
    List<Appointment> getAllAppointmentsByCompanyId(Long id);
    List<Appointment> getCurrentDateAppointmentsByCompanyAndStatusActive(LocalDate date, Long id);
    List<Appointment> getClientAppointmentRequestActive(Long accountId);
    boolean isAppointmentTakenByDateAndTime(LocalDate date, LocalTime time);
    boolean isAppointmentExisting(Long id);
    boolean isAppointmentAssociatedWithCurrentCompany(Long appointmentId, Long companyId);
    void acceptAppointment(Long appointmentId);
    void deleteAppointment(Long appointmentId);
    void completeAppointment(AppointmentCompleteDTO appointmentCompleteDTO, Long appointmentId);
    List<Appointment> getCurrentClientAppointmentsInfo(Long clientId, Long companyId, Status status);
}
