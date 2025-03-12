package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService,
                           RoleService roleService,
                           PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String allUsers(Model model, Principal principal) {

        User user = (User) userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("users", userService.findAll());

        System.out.println(user.getRoles());
        model.addAttribute("newUser", new User());
        model.addAttribute("userRoles", roleService.getAllRoles());
        return "admin";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute User user,
                             @RequestParam("roles") List<Long> rolesIds, Model model) {
        model.addAttribute("userRoles", roleService.getAllRoles());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>(roleService.findRoles(new ArrayList<>(rolesIds)));
        user.setRoles(roles);
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("userRoles", roleService.getAllRoles());
        return "editUserForm";
    }

    @PostMapping("/edit")
    public String editUser(@RequestParam("id") Long id,
                           @ModelAttribute("user") User user,
                           @RequestParam("roles") Set<Long> roleIds) {

        Set<Role> roles = roleService.findRoles(new ArrayList<>(roleIds));
        User existingUser = userService.findById(id);

        user.setRoles(roles);
        user.setPassword(existingUser.getPassword());
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") long id, Authentication authentication) {
        User currentUser = (User) userService.findByUsername(authentication.getName());

        if (currentUser.getId().equals(id)) {
            return "redirect:/logout";
        }

        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/index")
    public String index(Model model) {
        return "index";
    }

}
