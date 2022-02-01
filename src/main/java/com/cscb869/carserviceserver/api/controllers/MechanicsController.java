package com.cscb869.carserviceserver.api.controllers;

import com.cscb869.carserviceserver.data.entity.Account;
import com.cscb869.carserviceserver.data.entity.Mechanic;
import com.cscb869.carserviceserver.dto.AccountMechanicRegisterDTO;
import com.cscb869.carserviceserver.payloads.MechanicDetailsResponse;
import com.cscb869.carserviceserver.services.AccountService;
import com.cscb869.carserviceserver.services.MechanicService;
import com.cscb869.carserviceserver.services.QualificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class MechanicsController {

    private final AccountService accountService;
    private final MechanicService mechanicService;
    private final QualificationService qualificationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAccountMechanic(@Valid @RequestBody AccountMechanicRegisterDTO accountDTO) {
        if(accountService.isEmailTaken(accountDTO.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.CONFLICT);
        }
        if(qualificationService.isQualificationInvalid(accountDTO.getQualificationName())) {
            return new ResponseEntity<>("The given qualification is invalid!", HttpStatus.BAD_REQUEST);
        }

        accountService.saveAccountMechanic(accountDTO);
        accountService.addRoleToUser(accountDTO.getEmail(),accountDTO.getRoleName());

        Account account = accountService.getAccountByEmail(accountDTO.getEmail());
        mechanicService.saveMechanic(account);

        Long mechanicId = account.getId();
        mechanicService.addQualificationToMechanic(mechanicId, accountDTO.getQualificationName());


        return new ResponseEntity<>("Account created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMechanicDetails(@PathVariable Long id){
        Mechanic mechanic = mechanicService.getMechanicById(id);
        if(mechanic != null){
            return ResponseEntity.ok(new MechanicDetailsResponse(mechanic.getId(),mechanic.getQualifications()));
        }
        return new ResponseEntity<>("Account not found!", HttpStatus.NOT_FOUND);
    }


}
