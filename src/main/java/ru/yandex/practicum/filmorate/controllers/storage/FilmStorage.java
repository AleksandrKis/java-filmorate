package ru.yandex.practicum.filmorate.controllers.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.LinkedHashMap;
import java.util.Map;

public class FilmStorage {
    protected Map<Integer, Film> filmMap = new LinkedHashMap<>();
}
