package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.models.Film;

import java.util.List;

public interface FilmStorage {
    List<Film> getAll();

    Film getById(Long id);

    Film create(Film film);

    Film update(Film film);

}

