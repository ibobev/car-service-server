package com.cscb869.carserviceserver.services.implementations;

import com.cscb869.carserviceserver.data.entity.Account;
import com.cscb869.carserviceserver.data.entity.Appointment;
import com.cscb869.carserviceserver.data.entity.Car;
import com.cscb869.carserviceserver.data.entity.CarServiceCompany;
import com.cscb869.carserviceserver.data.repository.AccountRepository;
import com.cscb869.carserviceserver.data.repository.AppointmentRepository;
import com.cscb869.carserviceserver.data.repository.CarRepository;
import com.cscb869.carserviceserver.data.repository.CarServiceCompanyRepository;
import com.cscb869.carserviceserver.data.type.Status;
import com.cscb869.carserviceserver.dto.AppointmentCompleteDTO;
import com.cscb869.carserviceserver.dto.AppointmentCreateDTO;
import com.cscb869.carserviceserver.dto.AppointmentCreateDetailsDTO;
import com.cscb869.carserviceserver.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImplementation implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AccountRepository accountRepository;
    private final CarServiceCompanyRepository carServiceCompanyRepository;
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    @Override
    public Appointment saveAppointment(AppointmentCreateDTO appointmentCreateDTO) {
        Account account = accountRepository.findAccountById(appointmentCreateDTO.getClientId());
        CarServiceCompany carServiceCompany = carServiceCompanyRepository.findCarServiceCompanyById(appointmentCreateDTO.getCompanyId());
        Car car = carRepository.findCarById(appointmentCreateDTO.getCarId());
        LocalTime endTime = appointmentCreateDTO.getStartTime().plusHours(1);

        AppointmentCreateDetailsDTO createDetailsDTO = new AppointmentCreateDetailsDTO();
        createDetailsDTO.setAccount(account);
        createDetailsDTO.setCompany(carServiceCompany);
        createDetailsDTO.setCar(car);
        createDetailsDTO.setDate(appointmentCreateDTO.getDate());
        createDetailsDTO.setStartTime(appointmentCreateDTO.getStartTime());
        createDetailsDTO.setEndTime(endTime);
        createDetailsDTO.setStatus(Status.PENDING);
        return appointmentRepository.save(modelMapper.map(createDetailsDTO, Appointment.class));
    }

    @Override
    public Appointment getAppointment(Long id) {
        return appointmentRepository.findAppointmentById(id);
    }

    @Override
    public List<Appointment> getAppointmentsByCompanyAndStatus(Long companyId, Status status) {
        return appointmentRepository.findAppointmentsByCarServiceCompanyIdAndStatus(companyId,status);
    }

    @Override
    public List<Appointment> getAllAppointmentsByCompanyAndStatusPending(Long companyId) {
        return appointmentRepository.findAppointmentsByCarServiceCompanyIdAndStatus(companyId, Status.PENDING);
    }

    @Override
    public List<Appointment> getAllAppointmentsByCompanyId(Long id) {
        return appointmentRepository.findAppointmentsByCarServiceCompanyId(id);
    }

    @Override
    public List<Appointment> getCurrentDateAppointmentsByCompanyAndStatusActive(LocalDate date, Long id) {
        return appointmentRepository.findAppointmentsByDateAndCarServiceCompanyIdAndStatus(date,id, Status.ACTIVE);
    }

    @Override
    public List<Appointment> getClientAppointmentRequestActive(Long accountId) {
        return appointmentRepository.findAppointmentsByAccountIdAndStatus(accountId,Status.ACTIVE);
    }

    @Override
    public boolean isAppointmentTakenByDateAndTime(LocalDate date, LocalTime time) {
        if(appointmentRepository.findAppointmentByDateAndStartTime(date,time) != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean isAppointmentExisting(Long id) {
        if(appointmentRepository.findAppointmentById(id) != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isAppointmentAssociatedWithCurrentCompany(Long appointmentId, Long companyId) {
        if(appointmentRepository.findAppointmentById(appointmentId).getCarServiceCompany().getId().equals(companyId)){
            return true;
        }
        return false;
    }

    @Override
    public void acceptAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findAppointmentById(appointmentId);
        appointment.setStatus(Status.ACTIVE);
    }

    @Override
    public void deleteAppointment(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

    @Override
    public void completeAppointment(AppointmentCompleteDTO appointmentCompleteDTO, Long appointmentId) {
        Appointment appointment = appointmentRepository.findAppointmentById(appointmentId);

        appointment.setCost(appointmentCompleteDTO.getCost());
        appointment.setDetails(appointmentCompleteDTO.getDetails());
        appointment.setCategory(appointmentCompleteDTO.getServiceCategory());
        appointment.setStatus(Status.COMPLETED);

        appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getCurrentClientAppointmentsInfo(Long clientId, Long companyId, Status status) {
        return appointmentRepository.findAppointmentsByAccountIdAndCarServiceCompanyIdAndStatus(clientId,companyId,status);
    }

}
