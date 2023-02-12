package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private int id = 0;
    private HashMap<Integer, Film> films = new HashMap<>();

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
            if(film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
                throw new ValidationException("ФИЛЬМ НАДО МОЛОЖЕ");
            }else {
                ++id;
                film.setId(id);
                films.put(id,film);
            }
        return films.get(id);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        int keyId = film.getId();
            if(film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
                throw new ValidationException("ФИЛЬМУ НАДО БЫТЬ МОЛОЖЕ");
            }else if (!films.containsKey(keyId)) {
                throw new ValidationException("НЕТ ТАКОГО ФИЛЬМА "+keyId);
            }else {
                films.put(keyId,film);
            }
        return films.get(keyId);
    }

    @GetMapping
    public List<Film> filmsList() {
        List<Film> filmsList = new ArrayList<>(films.values());
        return filmsList;
    }

    public static void validation (Film film) {
        if(film.getName() == null || film.getName().isEmpty()) {
            throw new ValidationException("Bad film name");
        }
        if(film.getDescription().length() > 200) {
            throw new ValidationException("Too mach description");
        }
        if(film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            throw new ValidationException("Very old film");
        }
        if(film.getDuration() <= 0) {
            throw new ValidationException("Must be a positive");
        }
    }

}
