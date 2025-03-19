package ru.kata.spring.rest_api.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.rest_api.models.Role;
import ru.kata.spring.rest_api.models.User;
import ru.kata.spring.rest_api.repository.RoleRepository;
import ru.kata.spring.rest_api.services.UserService;

import java.security.Principal;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/admin/users")
public class RestController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public RestController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.create(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User updatedUser) {
        User existingUser = userService.findById(id);

        // Обновляем только те поля, которые были переданы
        if (updatedUser.getUsername() != null) {
            existingUser.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getRoles() != null) {
            existingUser.setRoles(updatedUser.getRoles());
        }
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            // Если пароль передан, обновляем его
            existingUser.setPassword(updatedUser.getPassword());
        }

        userService.update(existingUser, id);
        return new ResponseEntity<>(existingUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        String username = principal.getName();
        User user = (User) userService.loadUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
