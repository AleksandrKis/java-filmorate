package ru.yandex.practicum.filmorate.controllers;

import javax.validation.Valid;
import javax.validation.constraints.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private int id = 0;
    private Map<Integer, User> users = new HashMap<>();

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        try {
            if (validation(user)) {
                ++id;
                user.setId(id);
                if (user.getName() == null) {
                    user.setName(user.getLogin());
                }
                users.put(id, user);
                log.debug("Add user :" + users.get(id));
            }
        } catch (ValidationException e) {
            log.warn("Validation error : " + e.getMessage());
            throw new ValidationException(e.getMessage());
        }
        return users.get(id);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        int keyId = 0;
        try {
            if (validation(user)) {
                keyId = user.getId();
                if (users.containsKey(keyId)) {
                    users.put(keyId, user);
                    log.debug("Update user :" + users.get(id));
                } else {
                    log.warn("User is not registered an ID :" + keyId);
                    throw new ValidationException("not found ID :" + keyId);
                }
            }
        } catch (ValidationException e) {
            log.warn("Validation error : " + e.getMessage());
            throw new ValidationException(e.getMessage());
        }
        return users.get(keyId);
    }

    @GetMapping
    public List<User> usersList() {
        List<User> usersList = new ArrayList<>(users.values());
        return usersList;
    }

    public static boolean validation(User user) {
        if (user.getEmail() == null || (!user.getEmail().contains("@"))) {
            throw new ValidationException("email format is wrong");
        }
        if (user.getLogin().contains(" ") || user.getLogin().isEmpty()) {
            throw new ValidationException("login format is wrong");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Birthday can't be");
        }
        return true;
    }

}
