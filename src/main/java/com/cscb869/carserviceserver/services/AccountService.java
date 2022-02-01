package com.cscb869.carserviceserver.services;

import com.cscb869.carserviceserver.data.entity.Account;
import com.cscb869.carserviceserver.data.entity.Role;
import com.cscb869.carserviceserver.dto.AccountMechanicRegisterDTO;
import com.cscb869.carserviceserver.dto.AccountRegisterDTO;

import java.util.Collection;
import java.util.List;

public interface AccountService {
    Account saveAccount(AccountRegisterDTO account);
    Account saveAccountMechanic(AccountMechanicRegisterDTO account);
    Account getAccountById(Long id);
    Account getAccountByEmail(String email);
    Long getAccountID(String email);
    //List<Account> getAccounts();
    void addRoleToUser(String email, String roleName);
    boolean isEmailTaken(String email);
    Collection<Role> getAccountRoles(String email);
}
