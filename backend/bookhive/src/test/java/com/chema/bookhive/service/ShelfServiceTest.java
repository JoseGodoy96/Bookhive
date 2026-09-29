package com.chema.bookhive.service;

import com.chema.bookhive.repository.ShelfRepository;
import com.chema.bookhive.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    }

    @Test
    void createShelf_throwsException_whenNameAlreadyExistsForUser() {

    }
}
