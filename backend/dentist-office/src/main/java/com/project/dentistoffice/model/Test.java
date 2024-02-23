package com.project.dentistoffice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="test")
public class Test {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false, unique = true)
	private Long id;
	private int testInt;
	private String testStr;
	public int getTestInt() {
		return testInt;
	}
	public void setTestInt(int testInt) {
		this.testInt = testInt;
	}
	public String getTestStr() {
		return testStr;
	}
	public void setTestStr(String testStr) {
		this.testStr = testStr;
	}
}
