package com.cscb869.carserviceserver.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class AccountMechanicRegisterDTO {

    @NotEmpty
    private String roleName;

    @NotEmpty
    private String qualificationName;

    @NotEmpty
    @Size(min = 2)
    private String firstName;

    @NotEmpty
    @Size(min = 2)
    private String lastName;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min=5)
    private String password;
}
