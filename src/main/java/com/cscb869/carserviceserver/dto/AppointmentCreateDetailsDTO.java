package com.cscb869.carserviceserver.dto;

import com.cscb869.carserviceserver.data.entity.Account;
import com.cscb869.carserviceserver.data.entity.Car;
import com.cscb869.carserviceserver.data.entity.CarServiceCompany;
import com.cscb869.carserviceserver.data.type.Status;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentCreateDetailsDTO {

    private Account account;
    private CarServiceCompany company;
    private Car car;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Status status;

}
