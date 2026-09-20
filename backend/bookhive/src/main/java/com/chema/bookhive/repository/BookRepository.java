package com.chema.bookhive.repository;

import com.chema.bookhive.model.Book;
import com.chema.bookhive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByUser(User user);
}
