package com.cscb869.carserviceserver.services.implementations;

import com.cscb869.carserviceserver.data.repository.RoleRepository;
import com.cscb869.carserviceserver.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImplementation implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public boolean isRoleInvalid(String roleName) {
        if(roleRepository.findByRoleName(roleName) == null){
            return true;
        }else {
            return false;
        }
    }
}
