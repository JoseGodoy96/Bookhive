package com.chema.bookhive.service;

import com.chema.bookhive.exception.UserNotFoundException;
import com.chema.bookhive.model.Book;
import com.chema.bookhive.model.Shelf;
import com.chema.bookhive.model.User;
import com.chema.bookhive.repository.BookRepository;
import com.chema.bookhive.repository.ShelfRepository;
import com.chema.bookhive.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ShelfRepository shelfRepository;

    @Transactional(readOnly = true)
    public List<Book> findAllByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        return bookRepository.findByUser(user);
    }

    @Transactional
    public Book addBook(Long userId, Long shelfId, String title, String author, Integer pages) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        Shelf shelf = shelfRepository.findById(shelfId)
                .orElseThrow(() -> new );
    }
}
