package com.project.dentistoffice.repository;

import com.project.dentistoffice.model.Appointment;
import com.project.dentistoffice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByUserAndIsCancelled(User user, boolean isCancelled);
    Optional<Appointment> findByIdAndIsCancelled(Long id, boolean isCancelled);

    List<Appointment> findAllByIsCancelled(boolean isCancelled);
}
