package ru.yandex.practicum.filmorate.controllers;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
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
//    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern
//            ("yyyy-MM-dd");
//    private static final LocalDateDeserializer dateformatter = new LocalDateDeserializer(formatter);
    int id = 0;
    HashMap<Integer, User> users = new HashMap<>();

    @PostMapping
    public User create(@RequestBody User user) {
        try {
            if(validation(user)) {
                ++id;
                user.setId(id);
                if(user.getName().isEmpty()) {
                    user.setName(user.getLogin());
                }
                users.put(id,user);
            }
        }catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        }

        return users.get(id);
    }

    @PutMapping
    public User update(@RequestBody User user) {
        try {
            if(validation(user)) {
                id = user.getId();
                if(users.containsKey(id)) {
                    users.put(id,user);
                }
            }
        }catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        }

        return users.get(id);
    }

    @GetMapping
    public List<User> usersList() {
        List<User> usersList = new ArrayList<>(users.values());
        return usersList;
    }

    public static boolean validation (User user) {
        if(user.getEmail().isEmpty()||(!user.getEmail().contains("@"))) {
            throw new ValidationException("МЫЛО НЕ ТО");
        }
        if(user.getLogin().contains(" ")||user.getLogin().isEmpty()) {
            throw new ValidationException("ЛОГИН НЕ ТОТ");
        }
        if(user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("ЕЩЁ НЕ РОЖДЁН");
        }
        return true;
    }

}
