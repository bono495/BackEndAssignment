package com.bonoarts.assignment.service;

import com.bonoarts.assignment.exceptions.RecordNotFoundException;
import com.bonoarts.assignment.exceptions.UsernameNotFoundException;
import com.bonoarts.assignment.model.Role;
import com.bonoarts.assignment.model.User;
import com.bonoarts.assignment.repository.UserRepository;
import com.bonoarts.assignment.utils.RandomStringGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Collection<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUser(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean userExists(Integer id) {
        return userRepository.existsById(id);
    }

    @Override
    public Integer createUser(User user) {
        String password = user.getPassword();
        String encoded = passwordEncoder.encode(password);
        user.setPassword(encoded);

        User newUser = userRepository.save(user);
        return newUser.getId();
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(Integer id, User newUser) {
        if (!userRepository.existsById(id)) throw new RecordNotFoundException();
        User user = userRepository.findById(id).orElse(null);

        String password = user.getPassword();
        String encoded = passwordEncoder.encode(password);
        user.setPassword(encoded);

        userRepository.save(user);
    }
}