package com.project.dentistoffice.security;

import com.project.dentistoffice.model.User;
import com.project.dentistoffice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {this.userRepository = userRepository;}
	// Funkcija koja na osnovu username-a iz baze vraca objekat User-a
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findUserByEmail(email);
		
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
		} 
//		else if(!user.isActivated()) {
//			throw new UserNotActivatedException("User with email " + email+ " is not activated");
//		}
		else return user;
	}
}