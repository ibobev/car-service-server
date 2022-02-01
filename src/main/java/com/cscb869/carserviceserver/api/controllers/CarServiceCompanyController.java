package com.cscb869.carserviceserver.api.controllers;

import com.cscb869.carserviceserver.data.entity.Account;
import com.cscb869.carserviceserver.data.entity.CarServiceCompany;
import com.cscb869.carserviceserver.data.entity.Mechanic;
import com.cscb869.carserviceserver.dto.CarServiceCompanyRegisterDTO;
import com.cscb869.carserviceserver.payloads.CompanyAccountDetailsResponse;
import com.cscb869.carserviceserver.payloads.CompanyDetailsResponse;
import com.cscb869.carserviceserver.services.AccountService;
import com.cscb869.carserviceserver.services.CarServiceCompanyService;
import com.cscb869.carserviceserver.services.MechanicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/company")
public class CarServiceCompanyController {
    private final AccountService accountService;
    private final MechanicService mechanicService;
    private final CarServiceCompanyService carServiceCompanyService;

    @PostMapping("/")
    public ResponseEntity<?> registerCompany(@Valid @RequestBody CarServiceCompanyRegisterDTO carServiceCompanyRegisterDTO){

        if(carServiceCompanyService.isAccountAssociatedWithCompany(carServiceCompanyRegisterDTO.getOwnerId())){
            return new ResponseEntity<>("You already have registered a company!", HttpStatus.CONFLICT);
        }

        carServiceCompanyService.saveCompany(carServiceCompanyRegisterDTO);

        return new ResponseEntity<>("Company registered successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/owner/{accountId}")
    public ResponseEntity<?> getCompanyDetailsCurrentAccount(@PathVariable Long accountId) {

        CarServiceCompany carServiceCompany= carServiceCompanyService.getCarServiceCompanyByAccountId(accountId);

        if(carServiceCompany != null){
            return ResponseEntity.ok(new CompanyDetailsResponse(carServiceCompany.getCompanyName(), carServiceCompany.getCity(), carServiceCompany.getCreatedAt(), carServiceCompany.getId()));
        }
        return new ResponseEntity<>("No company is associated with the given account!", HttpStatus.NOT_FOUND );

    }

    @GetMapping("/")
    public ResponseEntity<List<CarServiceCompany>> getCompanies() {
        return ResponseEntity.ok().body(carServiceCompanyService.getAllCarServiceCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCompany(@PathVariable Long id){
        CarServiceCompany carServiceCompany = carServiceCompanyService.getCarServiceCompanyById(id);

        if(carServiceCompany != null) {
            Account account = carServiceCompany.getAccount();
            return ResponseEntity.ok(new CompanyAccountDetailsResponse(carServiceCompany.getCompanyName(), carServiceCompany.getCity(), carServiceCompany.getCreatedAt(), carServiceCompany.getId(), account));
        }

        return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id){
        if(carServiceCompanyService.deleteCarServiceCompany(id)){
            return new ResponseEntity<>("Company deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{companyId}/employee/{employeeId}")
    public ResponseEntity<?> addEmployee(@PathVariable Long companyId, @PathVariable Long employeeId){
        Mechanic mechanic = mechanicService.getMechanicById(employeeId);

        if(mechanic == null){
            return new ResponseEntity<>("No employee found with the given id!", HttpStatus.NOT_FOUND);
        }
        if(carServiceCompanyService.isEmployeeHired(mechanic)){
            return new ResponseEntity<>("Employee is already hired!", HttpStatus.CONFLICT);
        }
        if(carServiceCompanyService.addEmployeeToCompany(companyId, mechanic)){
            return new ResponseEntity<>("Employee added successfully!", HttpStatus.OK);
        }

        return new ResponseEntity<>("Unable to add employee!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<?> getEmployeeAssociatedCompany(@PathVariable Long employeeId) {
        CarServiceCompany carServiceCompany = carServiceCompanyService.getCarServiceCompanyByEmployeeId(employeeId);
        if(carServiceCompany != null){
            return ResponseEntity.ok(new CompanyDetailsResponse(carServiceCompany.getCompanyName(), carServiceCompany.getCity(), carServiceCompany.getCreatedAt(), carServiceCompany.getId()));
        }
        return new ResponseEntity<>("Account is not associated with any companies!", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{companyId}/employees")
    public ResponseEntity<?> getAllEmployees(@PathVariable Long companyId) {
        Set<Mechanic> mechanicSet = carServiceCompanyService.getEmployees(companyId);
        if(mechanicSet != null) {
            return ResponseEntity.ok(mechanicSet);
        }
        return new ResponseEntity<>("Company has no employees!", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{companyId}/employee/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long companyId,@PathVariable Long employeeId) {
        if(carServiceCompanyService.deleteEmployee(companyId, employeeId)) {
            return  new ResponseEntity<>("Employee deleted successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Employee not found!", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{companyId}/clients")
    public ResponseEntity<?> getAllClients(@PathVariable Long companyId) {
        Set<Account> accountSet = carServiceCompanyService.getClients(companyId);
        if(accountSet != null) {
            return ResponseEntity.ok(accountSet);
        }
        return new ResponseEntity<>("Company has no clients!", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{companyId}/client/{clientId}")
    public ResponseEntity<?> addClientToCompany(@PathVariable Long companyId, @PathVariable Long clientId) {
        Account account = accountService.getAccountById(clientId);
        if(account == null){
            return new ResponseEntity<>("No client found with the given id!", HttpStatus.NOT_FOUND);
        }
        if(carServiceCompanyService.isClientPresentInCompany(companyId,account)){
            return new ResponseEntity<>("Client already exists in this company!", HttpStatus.CONFLICT);
        }
        if(carServiceCompanyService.addClientToCompany(companyId,account)){
            return new ResponseEntity<>("Employee added successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Unable to add client!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getClientCompanies(@PathVariable Long clientId){
        Account account = accountService.getAccountById(clientId);
        List<CarServiceCompany> carServiceCompanies = carServiceCompanyService.getClientCarServiceCompanies(account);
        if(carServiceCompanies == null){
            return new ResponseEntity<>("No associated car service companies found!", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(carServiceCompanies);
    }
}
