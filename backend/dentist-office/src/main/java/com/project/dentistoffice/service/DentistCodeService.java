package com.project.dentistoffice.service;

import com.project.dentistoffice.exception.AlreadyUsedException;
import com.project.dentistoffice.exception.ObjectNotFoundException;
import com.project.dentistoffice.model.DentistCode;
import com.project.dentistoffice.repository.DentistCodeRepository;
import com.project.dentistoffice.repository.RoleRepository;
import com.project.dentistoffice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DentistCodeService {

    @Autowired
    private DentistCodeRepository dentistCodeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Value("${code}")
    private int code;

    public DentistCode findById(Long id) {
       return dentistCodeRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Code not found."));
    }

    public DentistCode findByCode(int code) {
        DentistCode dentistCode = dentistCodeRepository.findByCode(code).orElseThrow(()-> new ObjectNotFoundException("Code not found."));
        if (dentistCode.isUsed()) {
            throw new AlreadyUsedException("Code already used");
        }
        if (userRepository.findByRole(roleRepository.findByName("ROLE_DENTIST")) != null) {
            throw new AlreadyUsedException("Code already used");
        }
        dentistCode.isUsed(true);
        dentistCodeRepository.saveAndFlush(dentistCode);
        return dentistCode;
    }

    public void addCode() {
        List<DentistCode> dentistCodeList = dentistCodeRepository.findAll();
        if (dentistCodeList.size() == 0) {
            DentistCode dentistCode = new DentistCode();
            dentistCode.setCode(code);
            dentistCodeRepository.saveAndFlush(dentistCode);
        } else {
            System.out.println("tu je vec");
        }
    }
}
