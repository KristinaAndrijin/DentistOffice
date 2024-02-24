package com.project.dentistoffice.repository;

import com.project.dentistoffice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    //    List<Role> findByName(String name);
    Optional<Role> findById(Integer id);
    Role findByName(String name);
}
