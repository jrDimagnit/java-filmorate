package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {
    FilmStorage filmStorage;
    private final LocalDate checkDate = LocalDate.of(1895, 12, 28);

    FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Collection<Film> getAll() {
        return filmStorage.getAll().values();
    }

    public Film getById(Long id) {
        if (filmStorage.getAll().containsKey(id)) {
            return filmStorage.getById(id);
        } else {
            throw new NotFoundException("Фильм не найден!");
        }
    }

    public Film create(Film film) {
        if (film.getReleaseDate().isBefore(checkDate)) {
            throw new ValidationException("Указанна неверная дата!");
        } else {
            return filmStorage.create(film);
        }
    }

    public Film update(Film film) {
        if (film.getId() <= 0 || !filmStorage.getAll().containsKey(film.getId())) {
            throw new NotFoundException("Фильм не найден!");
        } else if (film.getReleaseDate().isBefore(checkDate)) {
            throw new ValidationException("Указанна неверная дата!");
        } else {
            return filmStorage.update(film);
        }
    }

    public void deleteAll() {
        filmStorage.deleteAll();
    }

    public void addLike(Long idFilm, Long idUser) {
        if (idFilm <= 0 || !filmStorage.getAll().containsKey(idFilm) || idUser <= 0) {
            throw new NotFoundException("Фильм не найден!");
        } else {
            Film film = filmStorage.getAll().get(idFilm);
            film.getLikes().add(idUser);
            Set<Long> checkSet = film.getLikes();
            System.out.println(checkSet);
        }
    }

    public void deleteLike(Long idFilm, Long idUser) {
        if (idFilm <= 0 || !filmStorage.getAll().containsKey(idFilm) || idUser <= 0) {
            throw new NotFoundException("Фильм не найден!");
        } else {
            filmStorage.getAll().get(idFilm).getLikes().remove(idUser);
        }
    }

    public Set<Film> getTopFilm(Integer count) {
        Set<Film> checkFilm = filmStorage.getAll().values().stream()
                .sorted((Comparator.comparingInt(o -> -o.getLikes().size())))
                .limit(count)
                .collect(Collectors.toSet());
        System.out.println(checkFilm);
        return checkFilm;
    }
}

