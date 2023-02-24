package ru.yandex.practicum.filmorate.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return filmService.update(film);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable("id") Integer id) {
        return filmService.findFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void likeFilmById(@PathVariable("id") Integer filmId, @PathVariable("userId") Integer userId) {
        filmService.addLikeFilmById(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void delLikeFilmById(@PathVariable("id") Integer filmId, @PathVariable("userId") Integer userId) {
        filmService.delLikeFilmById(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilmList(
            @RequestParam(value = "count", defaultValue = "10", required = false) Integer count) {
        return filmService.popularFilmList(count);
    }

    @GetMapping
    public List<Film> filmsList() {
        return filmService.filmsList();
    }

}
