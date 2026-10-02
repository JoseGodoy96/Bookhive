package com.chema.bookhive.service;

import com.chema.bookhive.exception.DuplicateShelfNameException;
import com.chema.bookhive.model.Shelf;
import com.chema.bookhive.model.User;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShelfServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ShelfRepository shelfRepository;

    @InjectMocks
    private ShelfService shelfService;

    @Test
    void createShelf_savesAndReturnsShelf_whenNameIsAvailable() {
        User owner = new User();
        owner.setId(1L);
        owner.setUsername("chema");

        Shelf savedShelf = new Shelf();
        savedShelf.setId(10L);
        savedShelf.setName("Favoritos");
        savedShelf.setDescription("Mis mejores libros");

        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(shelfRepository.save(any(Shelf.class))).thenReturn(savedShelf);
        when(shelfRepository.findByUserAndName(any(User.class), eq("Favoritos")))
                .thenReturn(Optional.empty());

        Shelf result = shelfService.createShelf(1L,  "Favoritos", "Mis mejores libros");

        assertThat(result.getName()).isEqualTo("Favoritos");
        verify(shelfRepository).save(any(Shelf.class));
    }

    @Test
    void createShelf_throwsException_whenNameAlreadyExistsForUser() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("chema");

        Shelf existingShelf = new Shelf();
        existingShelf.setName("Favoritos");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(shelfRepository.findByUserAndName(any(User.class), eq("Favoritos")))
                .thenReturn(Optional.of(existingShelf));

        assertThatThrownBy(() -> shelfService.createShelf(1L, "Favoritos", "Algo"))
                .isInstanceOf(DuplicateShelfNameException.class)
                .hasMessageContaining("Favoritos");

        verify(shelfRepository, never()).save(any(Shelf.class));
    }
}
