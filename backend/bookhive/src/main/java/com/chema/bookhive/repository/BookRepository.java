package com.chema.bookhive.repository;

import com.chema.bookhive.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
