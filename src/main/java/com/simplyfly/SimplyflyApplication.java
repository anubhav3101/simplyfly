package com.simplyfly;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.simplyfly.model.Role;
import com.simplyfly.model.user;
import com.simplyfly.repository.RoleRepository;
import com.simplyfly.repository.UserRepository;

@SpringBootApplication
public class SimplyflyApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SimplyflyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Seeding database with roles and users...");

        // Add Roles first to ensure they exist before adding users
        Role roleUser = new Role(null, "ROLE_USER");
        Role roleAdmin = new Role(null, "ROLE_ADMIN");

        // Check if the roles exist, and create if not
        if (!roleRepository.existsByName("ROLE_USER")) {
            roleRepository.save(roleUser);
            System.out.println("ROLE_USER added.");
        }
        if (!roleRepository.existsByName("ROLE_ADMIN")) {
            roleRepository.save(roleAdmin);
            System.out.println("ROLE_ADMIN added.");
        }

        // Add a test admin user, make sure the role exists before assignment
        if (!userRepository.existsByEmail("admin@example.com")) {
            user admin = new user();
            admin.setName("Admin");
            admin.setUsername("admin123");
            admin.setPhone("1234567890");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("adminpassword"));
            admin.setRoles(Set.of(roleRepository.findByName("ROLE_ADMIN").orElseThrow()));
            userRepository.save(admin);
            System.out.println("Admin user added.");
        }

        // Add a test regular user
        if (!userRepository.existsByEmail("user@example.com")) {
            user regularUser = new user();
            regularUser.setName("User");
            regularUser.setUsername("user123");
            regularUser.setPhone("0987654321");
            regularUser.setEmail("user@example.com");
            regularUser.setPassword(passwordEncoder.encode("userpassword"));
            regularUser.setRoles(Set.of(roleRepository.findByName("ROLE_USER").orElseThrow()));
            userRepository.save(regularUser);
            System.out.println("Regular user added.");
        }

        System.out.println("Database seeding completed.");
    }
}
