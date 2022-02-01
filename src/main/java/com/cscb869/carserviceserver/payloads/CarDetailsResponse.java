package com.cscb869.carserviceserver.payloads;


import com.cscb869.carserviceserver.data.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CarDetailsResponse {

    private Long id;
    private String plateNumber;
    private String brand;
    private String model;
    private LocalDate year;
}
