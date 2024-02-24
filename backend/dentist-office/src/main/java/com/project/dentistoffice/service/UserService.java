package com.project.dentistoffice.service;

import com.project.dentistoffice.dto.StringCodeDTO;
import com.project.dentistoffice.exception.ObjectNotFoundException;
import com.project.dentistoffice.model.Role;
import com.project.dentistoffice.model.User;
import com.project.dentistoffice.repository.RoleRepository;
import com.project.dentistoffice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User findByEmail(StringCodeDTO dto) {
        String email = dto.getStr();
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser = userRepository.saveAndFlush(newUser);
            if (dto.isCode()) {
                newUser.setRole(roleRepository.findByName("ROLE_DENTIST"));
                System.out.println("Dentist sign in!");
            } else {
                newUser.setRole(roleRepository.findByName("ROLE_PATIENT"));
                System.out.println("Patient sign in!");
            }
            newUser = userRepository.saveAndFlush(newUser);
            return newUser;
        } else {
            if (dto.isCode()) {
                user.setRole(roleRepository.findByName("ROLE_DENTIST"));
                user = userRepository.saveAndFlush(user);
                System.out.println("Dentist sign in!");
            }
            System.out.println(user.getRole().getName());
            return user;
        }
    }
}
