package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {

    public List<Role> getAllRoles();

    public Set<Role> findRoles(List<Long> roles);

    public void addRole(Role role);
}
