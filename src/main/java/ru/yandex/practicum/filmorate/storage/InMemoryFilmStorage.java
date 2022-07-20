package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.models.Film;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public void deleteAll() {
        storage.clear();
    }

    @Override
    public Set<Film> getTopFilm(Integer count) {
        return getAll().values().stream()
                .sorted((Comparator.comparingInt(o -> -o.getLikes().size())))
                .limit(count)
                .collect(Collectors.toSet());
    }
}
