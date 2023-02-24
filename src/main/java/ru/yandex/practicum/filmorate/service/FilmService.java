package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    FilmStorage filmStorage;
    UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public List<Film> filmsList() {
        return filmStorage.filmsList();
    }

    public Film findFilmById(Integer id) {
        if (filmStorage.getFilmMap().containsKey(id)) {
            return filmStorage.getFilmMap().get(id);
        } else {
            throw new FilmNotFoundException("Film not found.");
        }
    }

    public void addLikeFilmById(Integer filmId, Integer userId) {
        if (userStorage.getUserMap().containsKey(userId)) {
            Film film = findFilmById(filmId);
            if (film.getLikes().contains(userId)) {
                System.out.println("your like is have");
            } else {
                film.getLikes().add(userId);
            }
        } else {
            throw new UserNotFoundException("User not found.");
        }
    }

    public void delLikeFilmById(Integer filmId, Integer userId) {
        if (userStorage.getUserMap().containsKey(userId)) {
            Film film = findFilmById(filmId);
            if (!film.getLikes().contains(userId)) {
                System.out.println("your like isn't have");
            } else {
                film.getLikes().remove(userId);
            }
        } else {
            throw new UserNotFoundException("User not found.");
        }
    }

    private static int applyAsInt(Film f) {
        return f.getLikes().size();
    }

    public List<Film> popularFilmList(Integer count) {
        return filmStorage
                .getFilmMap()
                .values()
                .stream()
                .sorted(Comparator.comparingInt(FilmService::applyAsInt).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}
