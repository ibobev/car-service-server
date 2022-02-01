package com.cscb869.carserviceserver.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AccountLoginDTO {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
