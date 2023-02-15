package ru.yandex.practicum.filmorate;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTest {

    @Test
    public void invalidFieldsFilmTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Film film = new Film();
        film.setName("");
        film.setDescription("YandexPracticum".repeat(14));
        film.setDuration(-200);
        film.setReleaseDate(null);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(violations.size(), 4);
//        assertEquals("не должно быть пустым"
//                ,((ConstraintViolationImpl)violations.toArray()[1]).getMessage());//Name
//        assertEquals("должно быть не меньше 1"
//                ,((ConstraintViolationImpl)violations.toArray()[2]).getMessage());//Duration
//        assertEquals("размер должен находиться в диапазоне от 1 до 200"
//                ,((ConstraintViolationImpl)violations.toArray()[3]).getMessage());//Description
        violations.clear();
    }

    @Test
    public void setEmptyNameFilmValidate() {
        Film film = new Film();
        film.setName("");
        Exception exception = assertThrows(ValidationException.class, () -> FilmController.validation(film));
        assertEquals("Bad film name", exception.getMessage());
    }

    @Test
    public void setMachDescriptionFilmValidate() {
        Film film = new Film();
        film.setName("YandexPracticum");
        film.setDescription("YandexPracticum".repeat(14));
        Exception exception = assertThrows(ValidationException.class, () -> FilmController.validation(film));
        assertEquals("Too mach description", exception.getMessage());
    }

    @Test
    public void setOldReleaseDateFilmValidate() {
        Film film = new Film();
        film.setName("YandexPracticum");
        film.setDescription("YandexPracticum".repeat(12));
        film.setReleaseDate(LocalDate.of(1890, 03, 25));
        Exception exception = assertThrows(ValidationException.class, () -> FilmController.validation(film));
        assertEquals("Very old film", exception.getMessage());
    }

    @Test
    public void setNegativeDurationFilmValidate() {
        Film film = new Film();
        film.setName("YandexPracticum");
        film.setDescription("YandexPracticum".repeat(12));
        film.setReleaseDate(LocalDate.of(1990, 03, 25));
        film.setDuration(-200);
        Exception exception = assertThrows(ValidationException.class, () -> FilmController.validation(film));
        assertEquals("Must be a positive", exception.getMessage());
    }
}
