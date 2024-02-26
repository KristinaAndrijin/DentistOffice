package com.project.dentistoffice.repository;

import com.project.dentistoffice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    //    List<Role> findByName(String name);
    Optional<Role> findById(Long id);
    Role findByName(String name);
}
