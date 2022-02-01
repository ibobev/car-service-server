package com.cscb869.carserviceserver.services;

import com.cscb869.carserviceserver.data.entity.Car;
import com.cscb869.carserviceserver.dto.CarRegisterDTO;

import java.util.List;

public interface CarService {
    Car saveCar(CarRegisterDTO carRegisterDTO);
    Car getCarByCarId(Long id);
    List<Car> getAllAccountCars(Long id);
    boolean isCarRegistered(String plateNumber);
    boolean deleteCar(Long id);
}
