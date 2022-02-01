package com.cscb869.carserviceserver.data.repository;

import com.cscb869.carserviceserver.data.entity.Account;
import com.cscb869.carserviceserver.data.entity.CarServiceCompany;
import com.cscb869.carserviceserver.data.entity.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CarServiceCompanyRepository extends JpaRepository<CarServiceCompany, Long> {
    CarServiceCompany findCarServiceCompanyByAccountId(Long id);
    CarServiceCompany findCarServiceCompanyById(Long id);
    CarServiceCompany findCarServiceCompanyByMechanicsId(Long id);
    List<CarServiceCompany> findCarServiceCompaniesByClients(Account account);
}
