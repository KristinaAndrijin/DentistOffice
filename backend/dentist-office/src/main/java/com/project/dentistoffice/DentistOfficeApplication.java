package com.project.dentistoffice;

import com.project.dentistoffice.service.DentistCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DentistOfficeApplication implements ApplicationRunner {


	@Autowired
	private DentistCodeService dentistCodeService;

	public static void main(String[] args) {
		SpringApplication.run(DentistOfficeApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		dentistCodeService.addCode();
	}
}
