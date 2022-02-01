package com.cscb869.carserviceserver.services.implementations;

import com.cscb869.carserviceserver.data.entity.Car;
import com.cscb869.carserviceserver.data.repository.CarRepository;
import com.cscb869.carserviceserver.dto.CarRegisterDTO;
import com.cscb869.carserviceserver.services.CarService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CarServiceImplementation implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    @Override
    public Car saveCar(CarRegisterDTO carRegisterDTO) {
        return carRepository.save(modelMapper.map(carRegisterDTO, Car.class));
    }

    @Override
    public Car getCarByCarId(Long id) {
        return carRepository.findCarById(id);
    }

    @Override
    public List<Car> getAllAccountCars(Long id) {
        return carRepository.findAllByAccountId(id);
    }

    @Override
    public boolean isCarRegistered(String plateNumber) {
        if(carRepository.findByPlateNumber(plateNumber) != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCar(Long id) {

        if(carRepository.findById(id).isPresent()){
            carRepository.deleteById(id);
            return true;
        }
        return false;

    }


}
