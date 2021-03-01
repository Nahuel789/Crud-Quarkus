package org.acme.spring.data.jpa.service;

import org.acme.spring.data.jpa.exception.UserNotFoundException;
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
        User user1 = new User();
        user1.setName(user.getName());
        user1.setLastname(user.getLastname());
        user1.setAge(user.getAge());
        if (correctData(user1)) {
            return userDao.save(user1);
        } else {
            return null;
        }
    }

    public Optional<User> findById(Long id) throws UserNotFoundException {
        return userDao.findById(id);
    }

    public boolean correctData(User user) {
        Iterable<User> listI = userDao.findAll();
        for (User user1 : listI) {
            if (user1.getName().equals(user.getName()) && user1.getLastname().equals(user.getLastname())) {
                return false;
            }
        }
        return true;
    }
}
