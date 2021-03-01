package org.acme.spring.data.jpa.resource;

import org.acme.spring.data.jpa.exception.UserNotFoundException;
import org.acme.spring.data.jpa.model.User;
import org.acme.spring.data.jpa.service.UserService;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("/users")
public class UserResource {

    private final Logger logger = LoggerFactory.getLogger(UserResource.class);


    @Inject
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {

        List<User> result = new ArrayList<>();

        Iterable<User> list = userService.getAll();

        list.forEach(result::add);

        if (result.isEmpty()) {
            logger.info("no results");
            return Response.noContent().build();

        } else {
            logger.info("information returned successfully");
            return Response.ok().entity(result).build();
        }
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByName(@PathParam("name") String name) {

        List<User> users = userService.findByName(name);
        if (users.isEmpty()) {
            logger.info("there are no users with that name");
            return Response.noContent().build();
        } else {
            return Response.ok().entity(users).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("id") long id) throws UserNotFoundException {
        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isPresent()) {
            logger.info("user deleted successfully");
            userService.deleteById(id);
        } else {
            logger.warn("user not found");
            throw new UserNotFoundException(id);
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@RequestBody @Valid User user) {
        User us = userService.save(user);
        if (us != null) {
            logger.info("user created successfully");
            return Response.ok().entity(us).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Repeat user").build();
    }

    @PATCH
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeUser(@PathParam("id") Long id, @RequestBody @Valid User userChanged) throws UserNotFoundException {
        Optional<User> us = userService.findById(id);
        if (us.isPresent()) {
            us.get().setName(userChanged.getName());
            us.get().setLastname(userChanged.getLastname());
            us.get().setAge(userChanged.getAge());
            User usChanged = userService.save(us.get());
            if (usChanged != null) {
                return Response.ok().entity(usChanged).build();
            } else {
                return Response.notModified().entity("Repeat user").build();
            }
        } else {
            throw new UserNotFoundException(id);
        }
    }
}