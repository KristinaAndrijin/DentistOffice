package com.project.dentistoffice.service;

import com.project.dentistoffice.exception.AlreadyUsedException;
import com.project.dentistoffice.exception.ObjectNotFoundException;
import com.project.dentistoffice.model.DentistCode;
import com.project.dentistoffice.repository.DentistCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DentistCodeService {

    @Autowired
    private DentistCodeRepository dentistCodeRepository;

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
        dentistCode.isUsed(true);
        dentistCodeRepository.saveAndFlush(dentistCode);
        return dentistCode;
    }

    public void addCode() {
        DentistCode dentistCode = new DentistCode();
        dentistCode.setCode(code);
        dentistCodeRepository.saveAndFlush(dentistCode);
    }
}
