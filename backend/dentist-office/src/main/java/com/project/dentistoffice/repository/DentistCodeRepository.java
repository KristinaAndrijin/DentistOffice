package com.project.dentistoffice.repository;

import com.project.dentistoffice.model.DentistCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DentistCodeRepository extends JpaRepository<DentistCode, Long> {

    @Override
    Optional<DentistCode> findById(Long aLong);

    Optional<DentistCode> findByCode(int code);
}
