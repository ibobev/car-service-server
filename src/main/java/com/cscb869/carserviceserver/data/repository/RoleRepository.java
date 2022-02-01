package com.cscb869.carserviceserver.data.repository;

import com.cscb869.carserviceserver.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);

}
