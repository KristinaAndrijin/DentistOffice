package com.project.dentistoffice.controller;

import com.project.dentistoffice.dto.AppointmentDTO;
import com.project.dentistoffice.dto.AppointmentIdDTO;
import com.project.dentistoffice.dto.CodeDTO;
import com.project.dentistoffice.dto.ErrorDTO;
import com.project.dentistoffice.exception.ObjectNotFoundException;
import com.project.dentistoffice.model.Appointment;
import com.project.dentistoffice.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping()
    @PreAuthorize("hasAnyRole('DENTIST', 'PATIENT')")
    public ResponseEntity<?> addAppointment(Principal principal, @RequestBody AppointmentDTO dto){
        if (dto.getPatient().equals("None")) {
            dto.setPatient(principal.getName());
        }
        try {
            Appointment appointment = appointmentService.addAppointment(dto, principal.getName());
            return new ResponseEntity<>(appointment, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<ErrorDTO>(new ErrorDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    };

    @GetMapping("/week")
    @PreAuthorize("hasAnyRole('DENTIST', 'PATIENT')")
    public ResponseEntity<?> findAllWeek(Principal principal){
        List<AppointmentIdDTO> appointmentIdDTOList = appointmentService.findAll(principal.getName(), false);
        return new ResponseEntity<>(appointmentIdDTOList, HttpStatus.OK);
    };

    @GetMapping("/day")
    @PreAuthorize("hasAnyRole('DENTIST', 'PATIENT')")
    public ResponseEntity<?> findAllDay(Principal principal){
        List<AppointmentIdDTO> appointmentIdDTOList = appointmentService.findAll(principal.getName(), true);
        return new ResponseEntity<>(appointmentIdDTOList, HttpStatus.OK);
    };

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('DENTIST', 'PATIENT')")
    public ResponseEntity<?> Delete(Principal principal, @PathVariable("id") Long id){
        try {
            appointmentService.deleteAppointment(id, principal.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<ErrorDTO>(new ErrorDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    };

    @PostMapping("/period")
    @PreAuthorize("hasRole('DENTIST')")
    public ResponseEntity<?> period(Principal principal, @RequestBody CodeDTO period){
        appointmentService.changeCancellationPeriod(period.getCode());
        return new ResponseEntity<>(HttpStatus.OK);
    };

    @GetMapping("/period")
    @PreAuthorize("hasRole('DENTIST')")
    public ResponseEntity<?> periodGet(Principal principal){
        long period = appointmentService.getPeriod();
        return new ResponseEntity<>(period, HttpStatus.OK);
    };
}
