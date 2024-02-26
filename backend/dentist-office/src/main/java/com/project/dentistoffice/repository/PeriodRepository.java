package com.project.dentistoffice.repository;

import com.project.dentistoffice.model.CancellationPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeriodRepository extends JpaRepository<CancellationPeriod, Long> {
    Optional<CancellationPeriod> findById(Long id);
}
