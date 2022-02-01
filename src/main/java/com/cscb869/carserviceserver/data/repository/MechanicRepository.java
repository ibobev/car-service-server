package com.cscb869.carserviceserver.data.repository;

import com.cscb869.carserviceserver.data.entity.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MechanicRepository extends JpaRepository<Mechanic, Long> {
    Mechanic findMechanicById(Long id);
}
