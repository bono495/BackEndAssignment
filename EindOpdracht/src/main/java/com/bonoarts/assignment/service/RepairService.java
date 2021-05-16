package com.bonoarts.assignment.service;

import com.bonoarts.assignment.model.Repair;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface RepairService {

    public abstract Integer createRepair(Repair repair);
    public abstract void updateRepair(Integer id, Repair repair);
    public abstract void partialUpdateRepair(Integer id, Map<String, Object> fields);
    public abstract void deleteRepair(Integer id);
    public abstract Collection<Repair> getRepairs();
    public abstract Collection<Repair> getToDoRepairs();
    public abstract Collection<Repair> getFinishedRepairs();
    public abstract Optional<Repair> getRepairById(Integer id);
    public abstract boolean repairExistsById(Integer id);

}
