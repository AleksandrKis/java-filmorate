package ru.yandex.practicum.filmorate.controllers;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.validation.Valid;
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
    int id = 0;
    HashMap<Integer, User> users = new HashMap<>();

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        try {
            if(validation(user)) {
                ++id;
                user.setId(id);
                if(user.getName() == null) {
                    user.setName(user.getLogin());
                }
                users.put(id,user);
                log.debug("Сохранён пользователь: "+users.get(id));
            }
        }catch (ValidationException e) {
            log.warn("Ошибка валидации : "+e.getMessage());
            throw new ValidationException(e.getMessage());
        }
        return users.get(id);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        int keyId = 0;
        try {
            if(validation(user)) {
                keyId = user.getId();
                if(users.containsKey(keyId)) {
                    users.put(keyId,user);
                    log.debug("Обновлён пользователь: "+users.get(id));
                }else {
                    log.warn("НЕТ ТАКОГО USER "+keyId);
                    throw new ValidationException("НЕТ ТАКОГО USER "+keyId);
                }
            }
        }catch (ValidationException e) {
            log.warn("Ошибка валидации : "+e.getMessage());
            throw new ValidationException(e.getMessage());
        }
        return users.get(keyId);
    }

    @GetMapping
    public List<User> usersList() {
        List<User> usersList = new ArrayList<>(users.values());
        return usersList;
    }

    public static boolean validation (User user) {
        if(user.getLogin().contains(" ")||user.getLogin().isEmpty()) {
            throw new ValidationException("ЛОГИН НЕ ТОТ");
        }
        if(user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("ЕЩЁ НЕ РОЖДЁН");
        }
        return true;
    }

}
