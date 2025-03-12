package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    void save(User user);
    void updateUser(User user, Set<Role> roles);
    void deleteById(long id);

    List<User> findAll();

    User findById(long id);
    Object findByUsername(String username);
}