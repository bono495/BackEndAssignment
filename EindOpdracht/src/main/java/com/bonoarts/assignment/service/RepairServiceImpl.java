package com.bonoarts.assignment.service;

import com.bonoarts.assignment.exceptions.RecordNotFoundException;
import com.bonoarts.assignment.model.*;
import com.bonoarts.assignment.repository.RepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.Integer.parseInt;

@Service
public class RepairServiceImpl implements RepairService {

    @Autowired
    private RepairRepository repairRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CarService carService;

    @Autowired
    private ActionService actionService;

    @Override
    public Integer createRepair(Repair repair) {
        Repair newRepair = repairRepository.save(repair);
        return newRepair.getId();
    }

    @Override
    public void updateRepair(Integer id, Repair repair) {
        if (!repairRepository.existsById(id)) throw new RecordNotFoundException();
        Repair existingRepair = repairRepository.findById(id).get();
        existingRepair.setPayed(repair.getPayed());
        existingRepair.setStatus(repair.getStatus());
        existingRepair.setExecuted_by(repair.getExecuted_by());
        existingRepair.setCar(repair.getCar());
        existingRepair.setActions(repair.getActions());
        repairRepository.save(existingRepair);
    }

    @Override
    public void partialUpdateRepair(Integer id, Map<String, Object> fields) {
        if (!repairRepository.existsById(id)) throw new RecordNotFoundException();
        Repair repair = repairRepository.findById(id).get();

        for (Map.Entry<String, Object> entry: fields.entrySet()) {
            switch (entry.getKey()) {
                case "status":
                    repair.setStatus((String) entry.getValue());
                    break;
                case "payed":
                    repair.setPayed((Boolean) entry.getValue());
                    break;
                case "user":
                    User user = userService.getUser((Integer) entry.getValue()).get();
                    repair.setExecuted_by((User) user);
                    break;
                case "car":
                    Car car = carService.getCar((Integer) entry.getValue()).get();
                    repair.setCar((Car) car);
                    break;
                case "actions":
                    List<Action> actions = new ArrayList<Action>();
                    for (Integer action_id: (ArrayList<Integer>) entry.getValue()) {
                        System.out.println(action_id);
                        if (actionService.getAction(action_id).get() == null) throw new RecordNotFoundException();
                        Action action = actionService.getAction(action_id).get();
                        actions.add(action);
                    }

                    repair.setActions(actions);
                    break;
            }
        };

        repairRepository.save(repair);
    }


    @Override
    public void deleteRepair(Integer id) {
        if (!repairRepository.existsById(id)) throw new RecordNotFoundException();
        repairRepository.deleteById(id);
    }

    @Override
    public Collection<Repair> getRepairs() {
        return repairRepository.findAll();
    }

    @Override
    public Collection<Repair> getToDoRepairs() {
        Collection<Repair> repairs = new ArrayList<Repair>();
        for(Repair repair: repairRepository.findAll()) {
            if (repair.getStatus().equals("Agreed") || repair.getStatus().equals("Inspection")) {
                repairs.add(repair);
            }
        }

        return repairs;
    }

    @Override
    public Collection<Repair> getFinishedRepairs() {
        Collection<Repair> repairs = new ArrayList<Repair>();
        for(Repair repair: repairRepository.findAll()) {
            if (repair.getStatus().equals("Denied") || repair.getStatus().equals("Finished")) {
                repairs.add(repair);
            }
        }

        return repairs;
    }

    @Override
    public Optional<Repair> getRepairById(Integer id) {
        if (!repairRepository.existsById(id)) throw new RecordNotFoundException();
        return repairRepository.findById(id);
    }

    @Override
    public boolean repairExistsById(Integer id) {
        return repairRepository.existsById(id);
    }
}
