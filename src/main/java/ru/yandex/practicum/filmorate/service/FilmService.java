package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Service
public class FilmService implements BaseService<Film> {
    FilmStorage filmStorage;
    final LocalDate checkDate = LocalDate.of(1895, 12, 28);

    FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @Override
    public Collection<Film> getAll() {
        return filmStorage.getAll().values();
    }

    @Override
    public Film getById(Long id) {
        if (filmStorage.getAll().containsKey(id)) {
            return filmStorage.getById(id);
        } else {
            throw new NotFoundException("Фильм не найден!");
        }
    }

    @Override
    public Film create(Film film) {
        if (film.getReleaseDate().isBefore(checkDate)) {
            throw new ValidationException("Указанна неверная дата!");
        } else {
            return filmStorage.create(film);
        }
    }

    @Override
    public Film update(Film film) {
        if (film.getId() <= 0 || !filmStorage.getAll().containsKey(film.getId())) {
            throw new NotFoundException("Фильм не найден!");
        } else if (film.getReleaseDate().isBefore(checkDate)) {
            throw new ValidationException("Указанна неверная дата!");
        } else {
            return filmStorage.update(film);
        }
    }

    @Override
    public void deleteAll() {
        filmStorage.deleteAll();
    }

    public void addLike(Long idFilm, Long idUser) {
        if (idFilm <= 0 || !filmStorage.getAll().containsKey(idFilm) || idUser <= 0) {
            throw new NotFoundException("Фильм не найден!");
        } else {
            filmStorage.getAll().get(idFilm).getLikes().add(idUser);
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
        return filmStorage.getTopFilm(count);
    }
}

