package com.cscb869.carserviceserver.dto;

import com.cscb869.carserviceserver.data.type.Category;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class AppointmentCompleteDTO {

    @Min(value = 1, message = "Cost must be a greater than zero")
    private double cost;

    private String details;
    private Category serviceCategory;
    private Long companyId;
}
