package com.project.dentistoffice.service;

import com.project.dentistoffice.model.Test;
import com.project.dentistoffice.repository.TestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
	
	@Autowired
	private TestRepo testRepo;
	
	public Test test() {
		
		if (testRepo.findById(1L).isEmpty()){
			Test t = new Test();
			t.setTestInt(42);
			t.setTestStr("hi");
			testRepo.save(t);
			testRepo.flush();
		}
		
		return (Test) testRepo.findById(1L).get();
		
	}

}
