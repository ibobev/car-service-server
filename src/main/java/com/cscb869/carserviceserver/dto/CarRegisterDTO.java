package com.cscb869.carserviceserver.dto;

import com.cscb869.carserviceserver.data.entity.Account;
import lombok.Data;

@Data
public class CarRegisterDTO {

    private String plateNumber;

    private String brand;

    private String model;

    private String year;

    private Account account;
}
