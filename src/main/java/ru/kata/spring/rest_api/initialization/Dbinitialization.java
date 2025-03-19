package ru.kata.spring.rest_api.initialization;

import org.springframework.stereotype.Component;
import ru.kata.spring.rest_api.models.Role;
import ru.kata.spring.rest_api.models.User;
import ru.kata.spring.rest_api.services.RoleService;
import ru.kata.spring.rest_api.services.UserService;

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

        Role roleAdmin = new Role();
        roleAdmin.setRole("ROLE_ADMIN");

        Role roleUser = new Role();
        roleUser.setRole("ROLE_USER");

        roleService.addRole(roleUser);
        roleService.addRole(roleAdmin);
        System.out.println("Создаю роли: " + roleUser.getRole() + ", " + roleAdmin.getRole());

        User userAdmin = new User("admin", "admin", "admin@mail.ru");
        User user = new User("user", "user", "user@mail.ru");

        userAdmin.setRoles(Set.of(roleAdmin));
        user.setRoles(Set.of(roleUser));
        userService.create(user);
        userService.create(userAdmin);
        System.out.println("Роли пользователя: " + user.getRoles());
        System.out.println("Роль админа: " + userAdmin.getRoles());
    }
}
