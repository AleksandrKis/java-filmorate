package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmAlreadyHaveException;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public List<Film> filmsList() {
        return filmStorage.getAllFilms();
    }

    public Film findFilmById(Integer id) {
        if (filmStorage.getFilmMap().containsKey(id)) {
            return filmStorage.getFilmMap().get(id);
        } else {
            throw new FilmNotFoundException("Film not found.by Id "+id);
        }
    }

    public void addLikeFilmById(Integer filmId, Integer userId) {
        if (userStorage.getUserMap().containsKey(userId)) {
            Film film = findFilmById(filmId);
            if (film.getLikes().contains(userId)) {
                log.debug(userStorage.getUserMap()
                        .get(userId).getName()+" your like already have by film name : "+ findFilmById(filmId).getName());
                throw new FilmAlreadyHaveException(userStorage.getUserMap()
                        .get(userId).getName()+" your like already have by film name : "+ findFilmById(filmId).getName());
            } else {
                film.getLikes().add(userId);
            }
        } else {
            throw new UserNotFoundException("User not found by Id: "+userId);
        }
    }

    public void delLikeFilmById(Integer filmId, Integer userId) {
        if (userStorage.getUserMap().containsKey(userId)) {
            Film film = findFilmById(filmId);
            if (!film.getLikes().contains(userId)) {
                log.debug(userStorage.getUserMap()
                        .get(userId).getName()+"-this movie doesn't have your like : "+ findFilmById(filmId).getName());
                throw new UserNotFoundException(userStorage.getUserMap()
                        .get(userId).getName()+" your like not found by film : "+ findFilmById(filmId).getName());
            } else {
                film.getLikes().remove(userId);
            }
        } else {
            throw new UserNotFoundException("User not found by Id: "+userId);
        }
    }

    private static int applyAsInt(Film f) {
        return f.getLikes().size();
    }

    public List<Film> getPopularFilmList(Integer count) {
        return filmStorage
                .getFilmMap()
                .values()
                .stream()
                .sorted(Comparator.comparingInt(FilmService::applyAsInt).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}
