package com.simplyfly;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.simplyfly.model.user;
import com.simplyfly.repository.UserRepository;
import com.simplyfly.services.UserServiceImpl;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        // Arrange
        user newUser = new user();
        newUser.setUsername("testUser");
        newUser.setEmail("test@example.com");

        when(userRepository.existsByEmail("testUser")).thenReturn(false);
        when(userRepository.save(newUser)).thenReturn(newUser);

        // Act
        user registeredUser = userService.registerUser(newUser);

        // Assert
        assertEquals("testUser", registeredUser.getUsername());
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    void testRegisterUser_Failure_UserExists() {
        // Arrange
        user newUser = new user();
        newUser.setUsername("testUser");
        newUser.setEmail("test@example.com");

        when(userRepository.existsByEmail("testUser")).thenReturn(true);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(newUser);
        });
        assertEquals("Username already exists.", exception.getMessage());
        verify(userRepository, never()).save(newUser);
    }

    @Test
    void testFindByUsername_Success() {
        // Arrange
        String username = "testUser";
        user foundUser = new user();
        foundUser.setUsername(username);

        when(userRepository.findByName(username)).thenReturn(Optional.of(foundUser));

        // Act
        Optional<user> result = userService.findByUsername(username);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
    }

    @Test
    void testFindByUsername_Failure() {
        // Arrange
        String username = "nonExistentUser";

        when(userRepository.findByName(username)).thenReturn(Optional.empty());

        // Act
        Optional<user> result = userService.findByUsername(username);

        // Assert
        assertFalse(result.isPresent());
    }
}
