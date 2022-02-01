package com.cscb869.carserviceserver.data.repository;

import com.cscb869.carserviceserver.data.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    Car findByPlateNumber(String plateNumber);
    Car findCarById(Long id);
    List<Car> findAllByAccountId(Long id);
}
