package com.cscb869.carserviceserver.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentCreateDTO {

    private LocalDate date;
    private LocalTime startTime;
    private Long clientId;
    private Long companyId;
    private Long carId;

}
