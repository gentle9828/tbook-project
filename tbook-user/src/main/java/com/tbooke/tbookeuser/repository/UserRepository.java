package com.tbooke.tbookeuser.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tbooke.tbookeuser.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
}
