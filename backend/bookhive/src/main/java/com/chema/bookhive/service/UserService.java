package com.chema.bookhive.service;

import com.chema.bookhive.exception.UserNotFoundException;
import com.chema.bookhive.exception.UsernameAlreadyExistsException;
import com.chema.bookhive.model.User;
import com.chema.bookhive.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Username not found: " + username));
    }

    @Transactional
    public User createUser(String username, String email, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException("Username in user: " + username);
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        return userRepository.save(user);
    }
}
