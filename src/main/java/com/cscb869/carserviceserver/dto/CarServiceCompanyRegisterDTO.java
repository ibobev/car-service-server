package com.cscb869.carserviceserver.dto;

import com.cscb869.carserviceserver.data.entity.Account;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CarServiceCompanyRegisterDTO {

    private String companyName;

    private String city;

    private LocalDate createdAt;

    private Long ownerId;

    private Account account;
}
