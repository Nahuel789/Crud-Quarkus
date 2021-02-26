package org.acme.spring.data.jpa.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<NotFoundException> {

    private final Logger LOGGER = LoggerFactory.getLogger(NotFoundExceptionHandler.class);

    @Override
    public Response toResponse(NotFoundException exception) {
        LOGGER.warn("user not found:", exception);
        return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
    }
}
