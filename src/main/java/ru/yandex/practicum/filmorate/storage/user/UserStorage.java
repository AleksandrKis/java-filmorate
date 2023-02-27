package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface UserStorage {
    @PostMapping
    User create(@Valid @RequestBody User user);

    @PutMapping
    User update(@Valid @RequestBody User user);

    @GetMapping
    List<User> usersList();

    public Map<Integer, User> getUserMap();
}
