package org.acme.spring.data.jpa.repository;

import org.acme.spring.data.jpa.model.User;
import org.springframework.data.repository.CrudRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public interface UserDao extends CrudRepository<User, Long> {
    List<User> findByName(String name);
}
