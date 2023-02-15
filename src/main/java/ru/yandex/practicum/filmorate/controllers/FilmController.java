package ru.yandex.practicum.filmorate.controllers;

import javax.validation.Valid;
import javax.validation.constraints.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.yandex.practicum.filmorate.Constants.OLDEST_RELEASE;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private int id = 0;
    private Map<Integer, Film> films = new HashMap<>();

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        if (film.getReleaseDate().isBefore(OLDEST_RELEASE)) {
            log.warn("Validation error - ReleaseDate:" + film.getReleaseDate());
            throw new ValidationException("ReleaseDate must be in the present.");
        } else {
            ++id;
            film.setId(id);
            films.put(id, film);
            log.debug("Film add :" + films.get(id).getName());
        }
        return films.get(id);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        int keyId = film.getId();
        if (film.getReleaseDate().isBefore(OLDEST_RELEASE)) {
            log.warn("Validation error - ReleaseDate" + film.getReleaseDate());
            throw new ValidationException("ReleaseDate must be in the present.");
        } else if (!films.containsKey(keyId)) {
            log.warn("Film update error :" + film.getName());
            throw new ValidationException("A film with an ID :" + keyId + " is not registered.");
        } else {
            films.put(keyId, film);
            log.debug("Film update an ID :" + keyId);
        }
        return films.get(keyId);
    }

    @GetMapping
    public List<Film> filmsList() {
        List<Film> filmsList = new ArrayList<>(films.values());
        return filmsList;
    }

    public static void validation(Film film) {
        if (film.getName() == null || film.getName().isEmpty()) {
            throw new ValidationException("Bad film name");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Too mach description");
        }
        if (film.getReleaseDate().isBefore(OLDEST_RELEASE)) {
            throw new ValidationException("Very old film");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Must be a positive");
        }
    }

}
