package com.cscb869.carserviceserver.services;

import com.cscb869.carserviceserver.data.entity.Account;
import com.cscb869.carserviceserver.data.entity.CarServiceCompany;
import com.cscb869.carserviceserver.data.entity.Mechanic;
import com.cscb869.carserviceserver.dto.CarServiceCompanyRegisterDTO;

import java.util.List;
import java.util.Set;

public interface CarServiceCompanyService {
    CarServiceCompany saveCompany(CarServiceCompanyRegisterDTO carServiceCompanyRegisterDTO);
    CarServiceCompany getCarServiceCompanyByAccountId(Long id);
    CarServiceCompany getCarServiceCompanyByEmployeeId(Long id);
    CarServiceCompany getCarServiceCompanyById(Long id);
    boolean isAccountAssociatedWithCompany(Long id);
    List<CarServiceCompany> getAllCarServiceCompanies();
    boolean deleteCarServiceCompany(Long id);
    boolean addEmployeeToCompany(Long companyId, Mechanic mechanic);
    boolean isEmployeeHired(Mechanic mechanic);
    Set<Mechanic> getEmployees(Long id);
    boolean deleteEmployee(Long companyId, Long employeeId);
    Set<Account> getClients(Long id);
    boolean addClientToCompany(Long companyId, Account account);
    boolean isClientPresentInCompany(Long companyId,Account account);
    List<CarServiceCompany> getClientCarServiceCompanies(Account account);
}
