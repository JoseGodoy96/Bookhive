package com.chema.bookhive.repository;

import com.chema.bookhive.model.Shelf;
import com.chema.bookhive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {

    List<Shelf> findByUser(User user);
    Optional<Shelf> findByUserAndName(User user, String name);
}
