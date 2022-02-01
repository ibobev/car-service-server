package com.cscb869.carserviceserver.payloads;


import com.cscb869.carserviceserver.data.entity.Qualification;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class MechanicDetailsResponse {

    private Long id;
    private Set<Qualification> qualifications;
}
