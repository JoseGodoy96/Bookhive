package com.chema.bookhive.service;

import com.chema.bookhive.exception.DuplicateShelfNameException;
import com.chema.bookhive.exception.ForbiddenOperationException;
import com.chema.bookhive.exception.ShelfNotFoundException;
import com.chema.bookhive.exception.UserNotFoundException;
import com.chema.bookhive.model.Shelf;
import com.chema.bookhive.model.User;
import com.chema.bookhive.repository.ShelfRepository;
import com.chema.bookhive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShelfService {

    private final ShelfRepository shelfRepository;
    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    public List<Shelf> findAllByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        return shelfRepository.findByUser(user);
    }

    @Transactional
    public Shelf createShelf(Long userId, String name, String description) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        if (shelfRepository.findByUserAndName(user, name).isPresent()) {
            throw new DuplicateShelfNameException("Shelf with name '" + name + "' already exists for this user");
        }
        Shelf shelf = new Shelf();
        shelf.setName(name);
        shelf.setDescription(description);
        shelf.setUser(user);
        return shelfRepository.save(shelf);
    }

    @Transactional
    public void deleteShelf(Long shelfId, Long userId) {
        Shelf shelf = shelfRepository.findById(shelfId)
                .orElseThrow(() -> new ShelfNotFoundException("Shelf not found with id: " + shelfId));
        if (!shelf.getUser().getId().equals(userId)) {
            throw new ForbiddenOperationException("Shelf " + shelfId + " does not belong to user " + userId);
        }
        shelfRepository.delete(shelf);
    }
}
