package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static ru.yandex.practicum.filmorate.controllers.validate.FilmValidation.validation;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private int id = 0;
    private Map<Integer, Film> filmMap = new LinkedHashMap<>();

    public Map<Integer, Film> getFilmMap() {
        return filmMap;
    }

    @Override
    public Film create(Film film) {
        if (validation(film)) {
            ++id;
            film.setId(id);
            filmMap.put(id, film);
            log.debug("Film add :" + filmMap.get(id).getName());
        }
        return filmMap.get(id);
    }

    @Override
    public Film update(Film film) {
        int keyId = film.getId();
        if ((validation(film)) && (filmMap.containsKey(keyId))) {
            filmMap.put(keyId, film);
            log.debug("Film update an ID :" + keyId);
        } else {
            log.warn("Film update error :" + film.getName());
            throw new FilmNotFoundException("A film with an ID :" + keyId + " is not registered.");
        }
        return filmMap.get(keyId);
    }

    @Override
    public List<Film> filmsList() {
        List<Film> filmsList = new ArrayList<>(filmMap.values());
        return filmsList;
    }
}
