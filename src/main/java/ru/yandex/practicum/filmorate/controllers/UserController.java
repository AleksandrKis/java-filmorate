package ru.yandex.practicum.filmorate.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static ru.yandex.practicum.filmorate.controllers.validate.userValid.validation;

@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return userService.update(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Integer id) {
        return userService.findUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriendById(@PathVariable("id") Integer userId, @PathVariable("friendId") Integer friendId) {
        userService.addFriendById(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void delFriendById(@PathVariable("id") Integer userId, @PathVariable("friendId") Integer friendId) {
        userService.deleteFriendById(userId, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getUserFriendList(@PathVariable("id") Integer userId) {
        return userService.getFriendsListById(userId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("id") Integer userId, @PathVariable("otherId") Integer otherId) {
        return userService.getCommonFriends(userId, otherId);
    }

    @GetMapping
    public List<User> usersList() {
        return userService.usersList();
    }

}
