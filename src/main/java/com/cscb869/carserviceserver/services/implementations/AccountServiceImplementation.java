package com.cscb869.carserviceserver.services.implementations;

import com.cscb869.carserviceserver.data.entity.Account;

import com.cscb869.carserviceserver.data.entity.Role;
import com.cscb869.carserviceserver.data.repository.AccountRepository;
import com.cscb869.carserviceserver.data.repository.RoleRepository;
import com.cscb869.carserviceserver.dto.AccountMechanicRegisterDTO;
import com.cscb869.carserviceserver.dto.AccountRegisterDTO;
import com.cscb869.carserviceserver.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;


@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImplementation implements AccountService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    @Override
    public Account saveAccount(AccountRegisterDTO account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(modelMapper.map(account, Account.class));
    }

    @Override
    public Account saveAccountMechanic(AccountMechanicRegisterDTO account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(modelMapper.map(account, Account.class));
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findAccountById(id);
    }

    @Override
    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Long getAccountID(String email) {
        Account account = accountRepository.findByEmail(email);
        return  account.getId();
    }

    /*@Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }*/


    @Override
    public void addRoleToUser(String email, String roleName) {
        Account account = accountRepository.findByEmail(email);
        Role role = roleRepository.findByRoleName(roleName);

        account.getRoles().add(role);
    }



    @Override
    public boolean isEmailTaken(String email) {
        if(accountRepository.findByEmail(email) != null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Collection<Role> getAccountRoles(String email) {
        return accountRepository.findByEmail(email).getRoles();
    }
}
