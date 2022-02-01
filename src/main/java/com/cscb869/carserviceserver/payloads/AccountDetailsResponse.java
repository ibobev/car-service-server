package com.cscb869.carserviceserver.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDetailsResponse {

    private String firstName;
    private String lastName;
    private String email;

}
