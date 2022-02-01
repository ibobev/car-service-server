package com.cscb869.carserviceserver.services.implementations;

import com.cscb869.carserviceserver.data.entity.Account;
import com.cscb869.carserviceserver.data.entity.Mechanic;
import com.cscb869.carserviceserver.data.entity.Qualification;
import com.cscb869.carserviceserver.data.repository.MechanicRepository;
import com.cscb869.carserviceserver.data.repository.QualificationRepository;
import com.cscb869.carserviceserver.services.MechanicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MechanicServiceImplementation implements MechanicService {
    private final MechanicRepository mechanicRepository;
    private final QualificationRepository qualificationRepository;

    @Override
    public void saveMechanic(Account account) {
        Mechanic mechanic = new Mechanic();
        mechanic.setAccount(account);
        mechanicRepository.save(mechanic);
    }

    @Override
    public void addQualificationToMechanic(Long id, String qualificationName) {
        Optional<Mechanic> mechanic = mechanicRepository.findById(id);
        Qualification qualification = qualificationRepository.findByQualificationName(qualificationName);

        mechanic.ifPresent(value -> value.getQualifications().add(qualification));

    }

    @Override
    public Mechanic getMechanicById(Long id) {
        return mechanicRepository.findMechanicById(id);
    }
}
