package com.bonoarts.assignment.service;

import com.bonoarts.assignment.exceptions.UsernameNotFoundException;
import com.bonoarts.assignment.model.Role;
import com.bonoarts.assignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.bonoarts.assignment.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = null;
        Set<GrantedAuthority> grantedAuthorities = null;
        try
        {
            user = userRepository.findByUsername(userName);
            if(user == null)
                throw new UsernameNotFoundException(1);

            grantedAuthorities = new HashSet<>();
            for(Role role: user.getRoles()) {
                String roleName = "ROLE_" + role.getName();
                grantedAuthorities.add(new SimpleGrantedAuthority(roleName));
            }
        }
        catch(Exception exp) {
            exp.printStackTrace();
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), grantedAuthorities);
    }
}

