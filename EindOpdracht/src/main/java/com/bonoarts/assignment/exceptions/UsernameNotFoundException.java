package com.bonoarts.assignment.exceptions;

public class UsernameNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UsernameNotFoundException(Integer id) {
        super("Cannot find user " + id);
    }

}
