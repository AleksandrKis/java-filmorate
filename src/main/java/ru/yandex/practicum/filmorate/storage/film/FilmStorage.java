package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface FilmStorage {
    @PostMapping
    Film create(@Valid @RequestBody Film film);

    @PutMapping
    Film update(@Valid @RequestBody Film film);

    @GetMapping
    List<Film> filmsList();

    Map<Integer, Film> getFilmMap();
}
