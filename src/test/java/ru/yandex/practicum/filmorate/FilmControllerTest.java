package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmControllerTest {

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
        film.setReleaseDate(LocalDate.of(1890,03,25));
        Exception exception = assertThrows(ValidationException.class, () -> FilmController.validation(film));
        assertEquals("Very old film", exception.getMessage());
    }

    @Test
    public void setNegativeDurationFilmValidate() {
        Film film = new Film();
        film.setName("YandexPracticum");
        film.setDescription("YandexPracticum".repeat(12));
        film.setReleaseDate(LocalDate.of(1990,03,25));
        film.setDuration(-200);
        Exception exception = assertThrows(ValidationException.class, () -> FilmController.validation(film));
        assertEquals("Must be a positive", exception.getMessage());
    }
}
