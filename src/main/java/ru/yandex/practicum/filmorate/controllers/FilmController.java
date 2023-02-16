package ru.yandex.practicum.filmorate.controllers;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controllers.storage.FilmStorage;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;

import static ru.yandex.practicum.filmorate.utilities.Constants.OLDEST_RELEASE;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController extends FilmStorage {
    private int id = 0;

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        if (film.getReleaseDate().isBefore(OLDEST_RELEASE)) {
            log.warn("Validation error - ReleaseDate:" + film.getReleaseDate());
            throw new ValidationException("ReleaseDate must be in the present.");
        } else {
            ++id;
            film.setId(id);
            filmMap.put(id, film);
            log.debug("Film add :" + filmMap.get(id).getName());
        }
        return filmMap.get(id);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        int keyId = film.getId();
        if (film.getReleaseDate().isBefore(OLDEST_RELEASE)) {
            log.warn("Validation error - ReleaseDate" + film.getReleaseDate());
            throw new ValidationException("ReleaseDate must be in the present.");
        } else if (!filmMap.containsKey(keyId)) {
            log.warn("Film update error :" + film.getName());
            throw new ValidationException("A film with an ID :" + keyId + " is not registered.");
        } else {
            filmMap.put(keyId, film);
            log.debug("Film update an ID :" + keyId);
        }
        return filmMap.get(keyId);
    }

    @GetMapping
    public List<Film> filmsList() {
        List<Film> filmsList = new ArrayList<>(filmMap.values());
        return filmsList;
    }

}
