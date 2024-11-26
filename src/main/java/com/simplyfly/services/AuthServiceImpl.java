package com.simplyfly.services;



import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.simplyfly.dto.JWTAuthResponse;
import com.simplyfly.dto.LoginDto;
import com.simplyfly.dto.RegisterDto;
import com.simplyfly.dto.UserDTO;
import com.simplyfly.exceptions.BadRequestException;
import com.simplyfly.model.Role;
import com.simplyfly.model.user;
import com.simplyfly.repository.RoleRepository;
import com.simplyfly.repository.UserRepository;
import com.simplyfly.security.JwtTokenProvider;

import lombok.extern.slf4j.Slf4j;

@Service

@Slf4j

public class AuthServiceImpl implements AuthService {

	private AuthenticationManager authenticationManager;

	private UserRepository userRepo;

	private RoleRepository roleRepo;

	private PasswordEncoder passwordEncoder;

	private JwtTokenProvider jwtTokenProvider;

	@Autowired

	public AuthServiceImpl(AuthenticationManager authenticationManager,

			UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder,

			JwtTokenProvider jwtTokenProvider) {

		this.authenticationManager = authenticationManager;

		this.userRepo = userRepo;

		this.roleRepo = roleRepo;

		this.passwordEncoder = passwordEncoder;

		this.jwtTokenProvider = jwtTokenProvider;

	}

	@Override
	public JWTAuthResponse login(LoginDto dto) {

		System.out.println(("object received" + dto));

		Authentication authentication = authenticationManager.authenticate(

				new UsernamePasswordAuthenticationToken(dto.getName(), dto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication);

		System.out.println("Token generated : " + token);

		user user = userRepo.findByName(dto.getName()).get();

		System.out.println("user object found in repo " + user);

		UserDTO userDto = new UserDTO();

		userDto.setName(user.getName());

		userDto.setEmail(user.getEmail());

		userDto.setUsername(user.getUsername());

		String role = "ROLE_USER";

		Set<Role> roleUser = user.getRoles();

		for (Role roleTemp : roleUser)

		{

			if (roleTemp.getName().equalsIgnoreCase("ROLE_ADMIN"))

				role = "ROLE_ADMIN";

		}

		userDto.setRole(role);

		return new JWTAuthResponse(token, userDto);

	}

	@Override
	public String register(RegisterDto dto) {

	    // Check if username or email already exist
	    if (userRepo.existsByUsername(dto.getUsername())) {
	        throw new BadRequestException(HttpStatus.BAD_REQUEST, "Username already exists");
	    }

	    if (userRepo.existsByEmail(dto.getEmail())) {
	        throw new BadRequestException(HttpStatus.BAD_REQUEST, "Email already exists");
	    }

	    // Create a new user object
	    user newUser = new user();
	    newUser.setName(dto.getName());
	    newUser.setEmail(dto.getEmail());
	    newUser.setUsername(dto.getUsername());
	    newUser.setPassword(passwordEncoder.encode(dto.getPassword()));

	    // Save user first to generate user ID
	    user savedUser = userRepo.save(newUser);

	    // Assign default role 'ROLE_USER'
	    Role role = roleRepo.findByName("ROLE_USER")
	            .orElseThrow(() -> new BadRequestException(HttpStatus.BAD_REQUEST, "Role 'ROLE_USER' not found"));
	    Set<Role> roles = new HashSet<>();
	    roles.add(role);
	    savedUser.setRoles(roles); // Now set the roles after user is saved

	    // Save user with roles (user_roles relationship will be managed automatically)
	    userRepo.save(savedUser);

	    return "Registration successful!";
	}
}