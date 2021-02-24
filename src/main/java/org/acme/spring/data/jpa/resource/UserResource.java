package org.acme.spring.data.jpa.resource;

import org.acme.spring.data.jpa.exception.UserNotFoundException;
import org.acme.spring.data.jpa.model.User;
import org.acme.spring.data.jpa.service.UserService;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/users")
public class UserResource {

    @Inject
    private UserService userService;

    @GET
    public Response findAll() {
        return Response.ok(userService.getAll()).build();
    }

    @GET
    @Path("{name}")
    public Response findByName(@PathParam("name") String name) {
        if (userService.findByName(name).isEmpty()) {
            return Response.noContent().build();
        } else {
            return Response.ok(userService.findByName(name)).build();
        }
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") long id) {
        userService.deleteById(id);
    }

    @POST
    public Response create(@RequestBody User user) {
        return Response.ok(userService.save(user)).build();
    }

    @PUT
    @Path("id/{id}")
    public Response changeUser(@PathParam("id") Long id, @RequestBody User userChanged) throws UserNotFoundException {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            User us = user.get();
            us.setName(userChanged.getName());
            us.setLastname(userChanged.getLastname());
            us.setAge(userChanged.getAge());
            return Response.ok(userService.save(us)).build();
        } else {
            throw new UserNotFoundException(id);
        }
    }
}