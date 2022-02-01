package com.cscb869.carserviceserver.api.controllers;

import com.cscb869.carserviceserver.data.entity.Appointment;
import com.cscb869.carserviceserver.data.entity.Car;
import com.cscb869.carserviceserver.data.type.Status;
import com.cscb869.carserviceserver.dto.AppointmentCompleteDTO;
import com.cscb869.carserviceserver.dto.AppointmentCreateDTO;
import com.cscb869.carserviceserver.services.AppointmentService;
import com.cscb869.carserviceserver.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final CarService carService;

    @PostMapping("/")
    public ResponseEntity<?> createAppointment(@Valid @RequestBody AppointmentCreateDTO appointmentCreateDTO) {

        Car car = carService.getCarByCarId(appointmentCreateDTO.getCarId());

        if(car == null || !(car.getAccount().getId().equals(appointmentCreateDTO.getClientId()))){
            return new ResponseEntity<>("Car ID is not associated with your account!", HttpStatus.NOT_FOUND );
        }
        if(appointmentService.isAppointmentTakenByDateAndTime(appointmentCreateDTO.getDate(), appointmentCreateDTO.getStartTime())){
            return new ResponseEntity<>("Appointment is unavailable!", HttpStatus.CONFLICT);
        }
        if(appointmentCreateDTO.getDate().isBefore(LocalDate.now())){
            return new ResponseEntity<>("Appointment date cannot be set in the past!", HttpStatus.CONFLICT);
        }
        appointmentService.saveAppointment(appointmentCreateDTO);
        return new ResponseEntity<>("Appointment sent successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<?> getAllCompanyAppointments(@PathVariable Long companyId) {
        List<Appointment> appointmentList = appointmentService.getAllAppointmentsByCompanyId(companyId);
        if(appointmentList == null){
            return new ResponseEntity<>("Currently there are no appointments!", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(appointmentList);
    }

    @GetMapping("/client/{accountId}/active")
    public ResponseEntity<?> getClientActiveAppointments(@PathVariable Long accountId) {
        List<Appointment> appointmentList = appointmentService.getClientAppointmentRequestActive(accountId);
        if(appointmentList == null){
            return new ResponseEntity<>("Currently you have no active appointments!", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(appointmentList);
    }

    @GetMapping("/company/{companyId}/pending")
    public ResponseEntity<?> getPendingAppointments(@PathVariable Long companyId) {
        List<Appointment> appointmentList = appointmentService.getAllAppointmentsByCompanyAndStatusPending(companyId);
        if(appointmentList == null){
            return new ResponseEntity<>("Currently there are no pending appointments!", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(appointmentList);
    }

    @GetMapping("/company/{companyId}/active")
    public ResponseEntity<?> getActiveAppointments(@PathVariable Long companyId) {
        Status status = Status.ACTIVE;
        List<Appointment> appointmentList = appointmentService.getAppointmentsByCompanyAndStatus(companyId, status);
        if(appointmentList == null){
            return new ResponseEntity<>("Currently there are no active appointments!", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(appointmentList);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<?> getAppointment(@PathVariable Long appointmentId){
        Appointment appointment = appointmentService.getAppointment(appointmentId);
        if(appointment == null) {
            return new ResponseEntity<>("Appointment not found!", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(appointment);
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<?> completeAppointment(@PathVariable Long appointmentId, @Valid @RequestBody AppointmentCompleteDTO appointmentCompleteDTO ) {

        if(!appointmentService.isAppointmentExisting(appointmentId)){
            return new ResponseEntity<>("Invalid appointment ID!", HttpStatus.NOT_FOUND);
        }
        if(!appointmentService.isAppointmentAssociatedWithCurrentCompany(appointmentId,appointmentCompleteDTO.getCompanyId())){
            return new ResponseEntity<>("Appointment is not associated with the given company!", HttpStatus.BAD_REQUEST);
        }


        appointmentService.completeAppointment(appointmentCompleteDTO, appointmentId);

        return ResponseEntity.ok("Appointment completed!");
    }

    @PostMapping("/{appointmentId}")
    public ResponseEntity<?> acceptAppointment(@PathVariable Long appointmentId) {
        appointmentService.acceptAppointment(appointmentId);
        return ResponseEntity.ok("Appointment accepted!");
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<?> declineAppointment(@PathVariable Long appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.ok("Appointment declined!");
    }

    @GetMapping("/company/{companyId}/today")
    public ResponseEntity<?> getCompanyTodayAppointments(@PathVariable Long companyId){
        LocalDate today = LocalDate.now();
        List<Appointment> appointmentList = appointmentService.getCurrentDateAppointmentsByCompanyAndStatusActive(today,companyId);

        if(appointmentList == null){
            return new ResponseEntity<>("No active appointments found for today!", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(appointmentList);
    }

    @GetMapping("/company/{companyId}/client/{clientId}")
    public ResponseEntity<?> getClientAppointmentsInfo(@PathVariable Long companyId, @PathVariable Long clientId) {
        List<Appointment> appointmentList = appointmentService.getCurrentClientAppointmentsInfo(clientId,companyId,Status.COMPLETED);

        if(appointmentList == null){
            return new ResponseEntity<>("No info found!", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(appointmentList);
    }
}
