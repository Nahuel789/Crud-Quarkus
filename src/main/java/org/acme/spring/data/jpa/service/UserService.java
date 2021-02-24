package org.acme.spring.data.jpa.service;

import org.acme.spring.data.jpa.model.User;
import org.acme.spring.data.jpa.repository.UserDao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserService {

    @Inject
    private UserDao userDao;

    public Iterable<User> getAll() {
        return userDao.findAll();
    }

    public List<User> findByName(String color) {
        return userDao.findByName(color);
    }

    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    public User save(User user) {
        return userDao.save(user);
    }

    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }
}
