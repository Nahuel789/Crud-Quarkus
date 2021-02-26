package org.acme.spring.data.jpa.resource;

import org.acme.spring.data.jpa.exception.UserNotFoundException;
import org.acme.spring.data.jpa.model.User;
import org.acme.spring.data.jpa.service.UserService;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

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

    @Inject
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {

        List<User> result = new ArrayList<>();

        Iterable<User> list = userService.getAll();

        list.forEach(result::add);

        if (result.isEmpty()) {
            return Response.noContent().build();
        } else {
            return Response.ok().entity(result).build();

        }
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByName(@PathParam("name") String name) {

        List<User> users = userService.findByName(name);

        if (users.isEmpty()) {
            return Response.noContent().build();
        } else {
            return Response.ok().entity(users).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") long id) {

        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isPresent()) {
            userService.deleteById(id);
            return Response.ok().build();
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@RequestBody @Valid User user) {
        User user1 = userService.save(user);
        return Response.ok().entity(user1).build();
    }

    @PUT
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeUser(@PathParam("id") Long id, @RequestBody @Valid User userChanged)  {
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