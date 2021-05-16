package com.bonoarts.assignment.repository;

import com.bonoarts.assignment.model.Car;
import com.bonoarts.assignment.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarsRepository extends JpaRepository<Car, Integer> {
}
