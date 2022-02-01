package com.cscb869.carserviceserver.payloads;

import com.cscb869.carserviceserver.data.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CompanyAccountDetailsResponse {

    private String companyName;
    private String city;
    private LocalDate createdAt;
    private Long id;
    private Account account;
}
