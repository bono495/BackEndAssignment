package com.bonoarts.assignment.repository;

import com.bonoarts.assignment.model.Client;
import com.bonoarts.assignment.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourcesRepository extends JpaRepository<Resource, Integer> {
}
