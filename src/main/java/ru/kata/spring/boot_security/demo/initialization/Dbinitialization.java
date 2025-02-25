package ru.kata.spring.boot_security.demo.initialization;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class Dbinitialization {
    private final RoleService roleService;
    private final UserService userService;

    public Dbinitialization(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        Role roleUser = new Role("ROLE_USER");
        Role roleAdmin = new Role("ROLE_ADMIN");

        roleService.addRole(roleUser);
        roleService.addRole(roleAdmin);

        User userAdmin = new User("admin", "admin", "admin@mail.ru");
        User user = new User("user", "user", "user@mail.ru");

        userService.addUser(user, Set.of(roleUser));
        userService.addUser(userAdmin, Set.of(roleAdmin));
    }
}
