package com.cscb869.carserviceserver.services.implementations;

import com.cscb869.carserviceserver.data.entity.Account;
import com.cscb869.carserviceserver.data.entity.CarServiceCompany;
import com.cscb869.carserviceserver.data.entity.Mechanic;
import com.cscb869.carserviceserver.data.repository.AccountRepository;
import com.cscb869.carserviceserver.data.repository.CarServiceCompanyRepository;
import com.cscb869.carserviceserver.data.repository.MechanicRepository;
import com.cscb869.carserviceserver.dto.CarServiceCompanyRegisterDTO;
import com.cscb869.carserviceserver.services.CarServiceCompanyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class CarServiceCompanyServiceImplementation implements CarServiceCompanyService {

    private final AccountRepository accountRepository;
    private final MechanicRepository mechanicRepository;
    private final CarServiceCompanyRepository carServiceCompanyRepository;
    private final ModelMapper modelMapper;
    @Override
    public CarServiceCompany saveCompany(CarServiceCompanyRegisterDTO carServiceCompanyRegisterDTO) {
        carServiceCompanyRegisterDTO.setCreatedAt(LocalDate.now());
        Account account = accountRepository.findAccountById(carServiceCompanyRegisterDTO.getOwnerId());
        carServiceCompanyRegisterDTO.setAccount(account);
        return carServiceCompanyRepository.save(modelMapper.map(carServiceCompanyRegisterDTO, CarServiceCompany.class));
    }

    @Override
    public CarServiceCompany getCarServiceCompanyByAccountId(Long id) {
        return carServiceCompanyRepository.findCarServiceCompanyByAccountId(id);
    }


    @Override
    public CarServiceCompany getCarServiceCompanyByEmployeeId(Long id) {
        return carServiceCompanyRepository.findCarServiceCompanyByMechanicsId(id);
    }

    @Override
    public CarServiceCompany getCarServiceCompanyById(Long id) {
        return carServiceCompanyRepository.findCarServiceCompanyById(id);
    }

    @Override
    public boolean isAccountAssociatedWithCompany(Long id) {
        if(carServiceCompanyRepository.findCarServiceCompanyByAccountId(id) != null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<CarServiceCompany> getAllCarServiceCompanies() {
        return carServiceCompanyRepository.findAll();
    }

    @Override
    public boolean deleteCarServiceCompany(Long id) {
        if(carServiceCompanyRepository.findById(id).isPresent()){
            carServiceCompanyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean addEmployeeToCompany(Long companyId, Mechanic mechanic) {
        if(carServiceCompanyRepository.getById(companyId).getMechanics().add(mechanic)){
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmployeeHired(Mechanic mechanic) {

        List<CarServiceCompany> carServiceCompanies = carServiceCompanyRepository.findAll();
        return carServiceCompanies.stream().anyMatch(carServiceCompany -> carServiceCompany.getMechanics().contains(mechanic));
    }

    @Override
    public Set<Mechanic> getEmployees(Long id) {
        CarServiceCompany carServiceCompany = getCarServiceCompanyById(id);
        return carServiceCompany.getMechanics();
    }

    @Override
    public boolean deleteEmployee(Long companyId, Long employeeId) {
        CarServiceCompany carServiceCompany = carServiceCompanyRepository.findCarServiceCompanyById(companyId);
        Mechanic mechanic = mechanicRepository.findMechanicById(employeeId);
        if(carServiceCompany == null ) {
            return false;
        }
        if(mechanic == null) {
            return false;
        }
        if(!carServiceCompany.getMechanics().contains(mechanic)){
            return false;
        }


        carServiceCompanyRepository.findCarServiceCompanyById(companyId).getMechanics().remove(mechanic);
        return true;

    }

    @Override
    public Set<Account> getClients(Long id) {
        CarServiceCompany carServiceCompany = carServiceCompanyRepository.findCarServiceCompanyById(id);
        return carServiceCompany.getClients();
    }

    @Override
    public boolean addClientToCompany(Long companyId, Account account) {
        if(carServiceCompanyRepository.getById(companyId).getClients().add(account)){
            return true;
        }
        return false;
    }

    @Override
    public boolean isClientPresentInCompany(Long companyId,Account account) {
        if(carServiceCompanyRepository.findCarServiceCompanyById(companyId).getClients().contains(account)){
            return true;
        }
        return false;
    }

    @Override
    public List<CarServiceCompany> getClientCarServiceCompanies(Account account) {
        return carServiceCompanyRepository.findCarServiceCompaniesByClients(account);
    }


}
