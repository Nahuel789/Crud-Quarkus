package org.acme.spring.data.jpa.exception;

import javax.ws.rs.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(Long id) {
        super(String.format("The user with id %d is not found", id));
    }
}
