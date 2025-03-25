package ru.kata.spring.rest_api.services;

import ru.kata.spring.rest_api.models.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> getAllRoles();

    Set<Role> findRoles(List<Long> roles);

    void addRole(Role role);


}
