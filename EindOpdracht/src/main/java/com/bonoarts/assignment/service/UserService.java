package com.bonoarts.assignment.service;

import com.bonoarts.assignment.model.User;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    public abstract Integer createUser(User user);
    public abstract void updateUser(Integer id, User user);
    public abstract void deleteUser(Integer id);
    public abstract Collection<User> getUsers();
    public abstract Optional<User> getUser(Integer id);
    public abstract boolean userExists(Integer id);
}