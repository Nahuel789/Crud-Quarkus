package org.acme.spring.data.jpa.exception.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RuntimeExceptionHandler implements ExceptionMapper<RuntimeException> {

    private final Logger LOG = LoggerFactory.getLogger(RuntimeExceptionHandler.class);

    @Override
    public Response toResponse(RuntimeException exception) {
        LOG.error("database failure:" + exception.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database related error:" + exception.getMessage()).build();
    }
}
