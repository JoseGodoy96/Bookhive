package com.chema.bookhive.repository;

import com.chema.bookhive.model.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    void savesAndRetrievesUserByUsername() {
        User user = new User();
        user.setUsername("marta");
        user.setPassword("securePassword123");
        user.setEmail("marta@example.com");

        userRepository.save(user);
        Optional<User> result = userRepository.findByUsername("chema");

        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("chema");
        assertThat(result.get().getEmail()).isEqualTo("chema@example.com");
        assertThat(result.get().getId()).isNotNull();
    }
}
