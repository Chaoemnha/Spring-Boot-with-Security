package jmaster.io.restapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jmaster.io.restapi.model.User;
public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByUsername(String username);
}
