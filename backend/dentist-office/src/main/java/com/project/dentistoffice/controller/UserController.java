package com.project.dentistoffice.controller;

import com.project.dentistoffice.dto.*;
import com.project.dentistoffice.exception.AlreadyUsedException;
import com.project.dentistoffice.exception.ObjectNotFoundException;
import com.project.dentistoffice.model.DentistCode;
import com.project.dentistoffice.model.User;
import com.project.dentistoffice.security.TokenUtils;
import com.project.dentistoffice.service.DentistCodeService;
import com.project.dentistoffice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user/")
public class UserController {

    @Autowired
    private DentistCodeService dentistCodeService;
    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtils jwtTokenUtils;

    @PostMapping("verifyDentistCode")
    public ResponseEntity<?> verifyCode(@RequestBody CodeDTO codeDTO) {
        try {
            DentistCode dentistCode = dentistCodeService.findByCode(codeDTO.getCode());
            return new ResponseEntity<SuccessDTO>(new SuccessDTO("Successful"), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<ErrorDTO>(new ErrorDTO("Wrong code"), HttpStatus.NOT_FOUND);
        } catch (AlreadyUsedException e) {
            return new ResponseEntity<ErrorDTO>(new ErrorDTO("Already used code, go to sign in"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("handleSignIn")
    public ResponseEntity<?> handleSignIn(@RequestBody StringCodeDTO dto) {
        User user = userService.findByEmail(dto);
        String token = jwtTokenUtils.generateToken(user.getEmail());
        return new ResponseEntity<StringDTO>(new StringDTO(token), HttpStatus.OK);
    }
}
