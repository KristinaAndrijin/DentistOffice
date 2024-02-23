package com.project.dentistoffice.repository;

import com.project.dentistoffice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long>{

	User findUserByEmail(String email);

}
