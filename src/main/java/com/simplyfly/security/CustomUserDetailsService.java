package com.simplyfly.security;



import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.simplyfly.model.user;
import com.simplyfly.repository.UserRepository;



@Service

public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository UserRepository;

	// Constructor DI

	public CustomUserDetailsService(UserRepository userRepository) {

		super();

		this.UserRepository = userRepository;

	}

	@Override

	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

		user user = UserRepository.findBynameOrEmail(usernameOrEmail, usernameOrEmail)

				.orElseThrow(() -> new UsernameNotFoundException("User not exists by Username or Email"));

		Set<GrantedAuthority> authorities = user.getRoles().stream()

				.map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

		return new org.springframework.security.core.userdetails.User(usernameOrEmail, user.getPassword(), authorities);

	}

}
