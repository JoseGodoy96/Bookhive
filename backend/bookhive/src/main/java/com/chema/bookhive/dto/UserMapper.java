package com.chema.bookhive.dto;

import com.chema.bookhive.model.User;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
