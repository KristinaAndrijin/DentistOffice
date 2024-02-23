package com.project.dentistoffice.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;


@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="users")
@Entity
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false, unique = true)
	private Long id;
	private String name;
	private String surname;
	private String telephoneNumber;
	private String email;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonManagedReference
	private Role role;

	public User() {
		super();

	}

	public User(Long id, String name, String surname, String telephoneNumber, String email,
				Role role) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.telephoneNumber = telephoneNumber;
		this.email = email;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", password="
				+ ", role=" + role.toString() + "]";
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}