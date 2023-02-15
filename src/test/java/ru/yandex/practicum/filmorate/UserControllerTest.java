package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserControllerTest {

    @Test
    public void emptyEmailTest() {
        User user = new User();
        user.setEmail("");
        Exception exception = assertThrows(ValidationException.class, () -> UserController.validation(user));
        assertEquals("email format is wrong", exception.getMessage());
    }

    @Test
    public void wrongFormatEmailTest() {
        User user = new User();
        user.setEmail("practicum#email");
        Exception exception = assertThrows(ValidationException.class, () -> UserController.validation(user));
        assertEquals("email format is wrong", exception.getMessage());
    }

    @Test
    public void emptyLoginTest() {
        User user = new User();
        user.setEmail("practicum@yandex.ru");
        user.setLogin("");
        Exception exception = assertThrows(ValidationException.class, () -> UserController.validation(user));
        assertEquals("login format is wrong", exception.getMessage());
    }

    @Test
    public void withBlankLoginTest() {
        User user = new User();
        user.setEmail("practicum@yandex.ru");
        user.setLogin("practi cum");
        Exception exception = assertThrows(ValidationException.class, () -> UserController.validation(user));
        assertEquals("login format is wrong", exception.getMessage());
    }

    @Test
    public void birthdayFutureTest() {
        User user = new User();
        user.setEmail("practicum@yandex.ru");
        user.setLogin("practicum");
        user.setBirthday(LocalDate.of(2024,12,31));
        Exception exception = assertThrows(ValidationException.class, () -> UserController.validation(user));
        assertEquals("Birthday can't be", exception.getMessage());
    }
}
