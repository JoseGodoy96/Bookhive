package com.chema.bookhive.service;

import com.chema.bookhive.exception.BookNotFoundException;
import com.chema.bookhive.exception.ForbiddenOperationException;
import com.chema.bookhive.model.Book;
import com.chema.bookhive.model.Shelf;
import com.chema.bookhive.model.User;
import com.chema.bookhive.repository.BookRepository;
import com.chema.bookhive.repository.ShelfRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ShelfRepository shelfRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void addBook_savesAndReturnsBook_whenValidData() {
        // ARRANGE
        User owner = new User();
        owner.setId(1L);
        owner.setUsername("chema");

        Shelf shelf = new Shelf();
        shelf.setId(10L);
        shelf.setName("Ciencia ficción");
        shelf.setUser(owner);  // el shelf pertenece a chema

        Book savedBook = new Book();
        savedBook.setId(100L);
        savedBook.setTitle("Dune");

        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(shelfRepository.findById(10L)).thenReturn(Optional.of(shelf));
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        // ACT
        Book result = bookService.addBook(1L, 10L, "Dune", "Frank Herbert", 700);

        // ASSERT
        assertThat(result.getTitle()).isEqualTo("Dune");
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void addBook_throwsForbidden_whenShelfBelongsToOtherUser() {
        // ARRANGE
        User currentUser = new User();
        currentUser.setId(1L);

        User otherUser = new User();
        otherUser.setId(2L);

        Shelf shelf = new Shelf();
        shelf.setId(10L);
        shelf.setUser(otherUser);  // el shelf pertenece a otro

        when(userRepository.findById(1L)).thenReturn(Optional.of(currentUser));
        when(shelfRepository.findById(10L)).thenReturn(Optional.of(shelf));

        // ACT + ASSERT
        assertThatThrownBy(() -> bookService.addBook(1L, 10L, "Dune", "Frank Herbert", 700))
                .isInstanceOf(ForbiddenOperationException.class)
                .hasMessageContaining("does not belong");

        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void findByIdAndUser_throwsNotFound_whenBookDoesNotExist() {
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.findByIdAndUser(999L, 1L))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("999");
    }
}
