package com.project.dentistoffice.controller;

import com.project.dentistoffice.model.Test;
import com.project.dentistoffice.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/test/")
//@CrossOrigin(origins = "http://localhost:4200")
public class TestController {
	
	@Autowired
	private TestService testService;
	
	@GetMapping("hi")
    public ResponseEntity<?> callTest(){
		Test r = testService.test();
    	return new ResponseEntity<Test>(r, HttpStatus.OK);
    };

	@GetMapping("jwt")
//	@PreAuthorize("hasRole('DENTIST')")
	public ResponseEntity<?> callJwt(Principal principal){
		System.out.println(principal.getName());
		Test r = testService.test();
		return new ResponseEntity<Test>(r, HttpStatus.OK);
	};
};
