package com.project.dentistoffice.controller;

import com.project.dentistoffice.model.Test;
import com.project.dentistoffice.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test/")
//@CrossOrigin(origins = "http://localhost:4200")
public class TestController {
	
	@Autowired
	private TestService testService;
	
	@GetMapping("hi")
    public ResponseEntity<?> callSaul(){
		Test r = testService.test();
    	return new ResponseEntity<Test>(r, HttpStatus.OK);
    };
	
};
