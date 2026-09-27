package com.chema.bookhive.service;

import com.chema.bookhive.exception.UserNotFoundException;
import com.chema.bookhive.exception.UsernameAlreadyExistsException;
import com.chema.bookhive.model.User;
import com.chema.bookhive.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_savesAndReturnsUser_whenUsernameIsAvailable() {
        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("chema");
        savedUser.setEmail("chema@example.com");

        when(userRepository.findByUsername("chema")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.createUser("chema", "chema@example.com", "pass1234");

        assertThat(result.getUsername()).isEqualTo("chema");
        assertThat(result.getEmail()).isEqualTo("chema@example.com");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void findByUsername_returnsUser_whenExists() {
        User existingUser = new User();
        existingUser.setUsername("chema");
        existingUser.setEmail("chema@example.com");

        when(userRepository.findByUsername("chema")).thenReturn(Optional.of(existingUser));

        User result = userService.findByUsername("chema");

        assertThat(result.getUsername()).isEqualTo("chema");
        assertThat(result.getEmail()).isEqualTo("chema@example.com");
    }

    @Test
    void createUser_throwsException_whenUsernameAlreadyExists() {
        User existingUser = new User();
        existingUser.setUsername("chema");

        when(userRepository.findByUsername("chema")).thenReturn(Optional.of(existingUser));

        assertThatThrownBy(() -> userService.createUser("chema", "chema@example.com", "pass1234"))
                .isInstanceOf(UsernameAlreadyExistsException.class)
                .hasMessageContaining("chema");
    }

    @Test
    void findByUsername_throwsException_whenUserDoesNotExist() {
        when(userRepository.findByUsername("chema")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findByUsername("chema"))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("chema");
    }
}
