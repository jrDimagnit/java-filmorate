package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.models.Film;

import java.util.HashMap;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private Long id = 0L;
    private HashMap<Long, Film> storage = new HashMap<>();

    @Override
    public HashMap<Long, Film> getAll() {
        return storage;
    }

    @Override
    public Film getById(Long id) {
        return storage.get(id);
    }

    @Override
    public Film create(Film film) {
        film.setId(++id);
        storage.put(id, film);
        return film;
    }

    @Override
    public Film update(Film film) {
        storage.put(film.getId(), film);
        return film;
    }

    public void deleteAll() {
        storage.clear();
    }
}
