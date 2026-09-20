package com.chema.bookhive.repository;

import com.chema.bookhive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
