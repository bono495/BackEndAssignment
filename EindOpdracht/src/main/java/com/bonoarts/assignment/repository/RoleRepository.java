package com.bonoarts.assignment.repository;

import com.bonoarts.assignment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<User, Integer> {
}
