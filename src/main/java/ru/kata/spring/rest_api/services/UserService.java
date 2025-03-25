package ru.kata.spring.rest_api.services;

import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.rest_api.models.User;

import java.util.List;

public interface UserService {

    void create(User user);

    void delete(long id);

    void update(User user, long id);

    List<User> findAll();

    User findById(long id);

    Object findByUsername(String username);

    User oneUser();

    UserDetails loadUserByUsername(String username);

    User updateUserFields(User existingUser, User updatedUser);
}