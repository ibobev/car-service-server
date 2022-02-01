package com.cscb869.carserviceserver.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CompanyDetailsResponse {

    private String companyName;
    private String city;
    private LocalDate createdAt;
    private Long id;

}
