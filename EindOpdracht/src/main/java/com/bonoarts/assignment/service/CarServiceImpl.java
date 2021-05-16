package com.bonoarts.assignment.service;

import com.bonoarts.assignment.exceptions.RecordNotFoundException;
import com.bonoarts.assignment.exceptions.UsernameNotFoundException;
import com.bonoarts.assignment.model.Car;
import com.bonoarts.assignment.model.Client;
import com.bonoarts.assignment.model.Role;
import com.bonoarts.assignment.repository.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.Integer.parseInt;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarsRepository carRepository;

    private ClientService clientService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Collection<Car> getCars() {
        return carRepository.findAll();
    }

    @Override
    public Optional<Car> getCar(Integer id) {
        return carRepository.findById(id);
    }

    @Override
    public boolean carExists(Integer id) {
        return carRepository.existsById(id);
    }

    @Override
    public Integer createCar(Car car) {
        Car newCar = carRepository.save(car);
        return newCar.getId();
    }

    @Override
    public void deleteCar(Integer id) {
        carRepository.deleteById(id);
    }

    @Override
    public void updateCar(Integer id, Car newCar) {
        if (!carRepository.existsById(id)) throw new RecordNotFoundException();
        Car car = carRepository.findById(id).get();

        car.setBrand(newCar.getBrand());
        car.setPapers(newCar.getPapers());
        car.setYear(newCar.getYear());
        car.setClient(newCar.getClient());

        carRepository.save(car);
    }

    @Override
    public void partialUpdateCar(Integer id, Map<String, String> fields) {
        if (!carRepository.existsById(id)) throw new RecordNotFoundException();
        Car car = carRepository.findById(id).get();

        for (String field : fields.keySet()) {
            switch (field.toLowerCase()) {
                case "brand":
                    car.setBrand((String) fields.get(field));
                    break;
                case "papers":
                    car.setPapers((String) fields.get(field));
                    break;
                case "year":
                    car.setYear((Integer) parseInt(fields.get(field)));
                    break;
                case "client_id":
                    Optional<Client> client = null;
                    try
                    {
                        client = clientService.getClient(parseInt(fields.get(field)));
                        if(client == null)
                            throw new UsernameNotFoundException(1);
                        else
                            car.setClient(client.get());
                    }
                    catch(Exception exp) {
                        exp.printStackTrace();
                    }
                    break;
            }
        }
        carRepository.save(car);
    }
}