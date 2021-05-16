package com.bonoarts.assignment.repository;

import com.bonoarts.assignment.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientsRepository extends JpaRepository<Client, Integer> {

}
