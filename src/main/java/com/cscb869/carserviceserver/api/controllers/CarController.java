package com.cscb869.carserviceserver.api.controllers;

import com.cscb869.carserviceserver.data.entity.Account;
import com.cscb869.carserviceserver.data.entity.Car;
import com.cscb869.carserviceserver.dto.CarRegisterDTO;
import com.cscb869.carserviceserver.services.AccountService;
import com.cscb869.carserviceserver.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/car")
public class CarController {

    private final CarService carService;
    private final AccountService accountService;

    @PostMapping("/client/{id}")
    public ResponseEntity<?> registerCar(@PathVariable Long id,@Valid @RequestBody CarRegisterDTO carRegisterDTO) {
        if(carService.isCarRegistered(carRegisterDTO.getPlateNumber())){
            return new ResponseEntity<>("A car with the given plate number already exists!", HttpStatus.CONFLICT);
        }

        Account account = accountService.getAccountById(id);
        carRegisterDTO.setAccount(account);
        carService.saveCar(carRegisterDTO);

        return new ResponseEntity<>("Car registered successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<List<Car>> getCurrentAccountCars(@PathVariable Long id){

        return ResponseEntity.ok().body(carService.getAllAccountCars(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable Long id){
        if(carService.deleteCar(id)){
            return new ResponseEntity<>("Car deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Car not found", HttpStatus.NOT_FOUND);
    }

}
