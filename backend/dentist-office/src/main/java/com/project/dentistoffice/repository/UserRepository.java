package com.project.dentistoffice.repository;

import com.project.dentistoffice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>{

	User findUserByEmail(String email);

}
