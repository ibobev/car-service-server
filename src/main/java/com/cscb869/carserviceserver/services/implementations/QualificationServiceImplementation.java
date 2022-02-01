package com.cscb869.carserviceserver.services.implementations;

import com.cscb869.carserviceserver.data.repository.QualificationRepository;
import com.cscb869.carserviceserver.services.QualificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class QualificationServiceImplementation implements QualificationService {
    private final QualificationRepository qualificationRepository;
    @Override
    public boolean isQualificationInvalid(String qualificationName) {
        if(qualificationRepository.findByQualificationName(qualificationName) == null){
            return true;
        }
        return false;
    }
}
