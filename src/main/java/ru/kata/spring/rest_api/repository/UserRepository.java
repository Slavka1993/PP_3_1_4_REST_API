package ru.kata.spring.rest_api.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kata.spring.rest_api.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"roles"})
    List<User> findAll();

    @EntityGraph(attributePaths = {"roles"})
    User findByUsername(String username);
}
