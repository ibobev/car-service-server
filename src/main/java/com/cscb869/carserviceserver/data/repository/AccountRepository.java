package com.cscb869.carserviceserver.data.repository;

import com.cscb869.carserviceserver.data.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);
    Account findAccountById(Long id);

}
