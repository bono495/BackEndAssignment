package com.bonoarts.assignment.service;

import com.bonoarts.assignment.model.Car;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface CarService {

    public abstract Integer createCar(Car Car);
    public abstract void updateCar(Integer id, Car Car);
    public abstract void partialUpdateCar(Integer id, Map<String, String> fields);
    public abstract void deleteCar(Integer id);
    public abstract Collection<Car> getCars();
    public abstract Optional<Car> getCar(Integer id);
    public abstract boolean carExists(Integer id);
}