package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.models.Film;

import java.util.HashMap;
import java.util.Set;

public interface FilmStorage {
    HashMap<Long, Film> getAll();

    Film getById(Long id);

    Film create(Film film);

    Film update(Film film);

    void deleteAll();

    Set<Film> getTopFilm(Integer count);
}

