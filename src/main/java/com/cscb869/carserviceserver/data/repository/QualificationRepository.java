package com.cscb869.carserviceserver.data.repository;

import com.cscb869.carserviceserver.data.entity.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QualificationRepository extends JpaRepository<Qualification, Long> {
    Qualification findByQualificationName(String qualificationName);
}
