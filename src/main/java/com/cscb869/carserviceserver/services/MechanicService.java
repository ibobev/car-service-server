package com.cscb869.carserviceserver.services;

import com.cscb869.carserviceserver.data.entity.Account;
import com.cscb869.carserviceserver.data.entity.Mechanic;

public interface MechanicService {
    void saveMechanic(Account account);
    void addQualificationToMechanic(Long id, String qualificationName);
    Mechanic getMechanicById(Long id);
}
