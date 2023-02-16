package ru.yandex.practicum.filmorate.controllers;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controllers.storage.UserStorage;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.yandex.practicum.filmorate.controllers.validate.userValid.validation;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends UserStorage {
    private int id = 0;
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        try {
            if (validation(user)) {
                ++id;
                user.setId(id);
                if (user.getName() == null) {
                    user.setName(user.getLogin());
                }
                userMap.put(id, user);
                log.debug("Add user :" + userMap.get(id));
            }
        } catch (ValidationException e) {
            log.warn("Validation error : " + e.getMessage());
            throw new ValidationException(e.getMessage());
        }
        return userMap.get(id);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        int keyId = 0;
        try {
            if (validation(user)) {
                keyId = user.getId();
                if (userMap.containsKey(keyId)) {
                    userMap.put(keyId, user);
                    log.debug("Update user :" + userMap.get(id));
                } else {
                    log.warn("User is not registered an ID :" + keyId);
                    throw new ValidationException("not found ID :" + keyId);
                }
            }
        } catch (ValidationException e) {
            log.warn("Validation error : " + e.getMessage());
            throw new ValidationException(e.getMessage());
        }
        return userMap.get(keyId);
    }

    @GetMapping
    public List<User> usersList() {
        List<User> usersList = new ArrayList<>(userMap.values());
        return usersList;
    }

}
