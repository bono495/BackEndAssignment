package com.bonoarts.assignment.repository;

import com.bonoarts.assignment.model.Repair;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface RepairRepository extends JpaRepository<Repair, Integer> {

    public Collection<Repair> findAllByStatus(String name);

}
