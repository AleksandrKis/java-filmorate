package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

import static ru.yandex.practicum.filmorate.controllers.validate.userValid.validation;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private int id = 0;
    private final Map<Integer, User> userMap = new LinkedHashMap<>();

    public Map<Integer, User> getUserMap() {
        return userMap;
    }

    @Override
    public User create(User user) {
        if (validation(user)) {
            ++id;
            user.setId(id);
            if (user.getName().isEmpty()) {
                user.setName(user.getLogin());
                userMap.put(id, user);
                log.debug("Add user with loginName :" + userMap.get(id));
            } else {
                userMap.put(id, user);
                log.debug("Add user :" + userMap.get(id));
            }
        }
        return userMap.get(id);
    }

    @Override
    public User update(User user) {
        int keyId = 0;
        if (validation(user)) {
            keyId = user.getId();
            if (userMap.containsKey(keyId)) {
                userMap.put(keyId, user);
                log.debug("Update user :" + userMap.get(id));
            } else {
                log.warn("User is not registered an ID :" + keyId);
                throw new UserNotFoundException("not found ID :" + keyId);
            }
        }
        return userMap.get(keyId);
    }

    @Override
    public List<User> usersList() {
        List<User> usersList = new ArrayList<>(userMap.values());
        return usersList;
    }
}
