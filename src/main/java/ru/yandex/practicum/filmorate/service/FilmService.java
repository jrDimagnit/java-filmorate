package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.dao.LikeDao;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Genre;
import ru.yandex.practicum.filmorate.models.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FilmService implements BaseService<Film> {
    FilmStorage filmStorage;
    GenreDao genreDao;
    LikeDao likeDao;
    MpaDao mpaDao;

    final LocalDate checkDate = LocalDate.of(1895, 12, 28);

    public FilmService(FilmStorage filmStorage, GenreDao genreDao, LikeDao likeDao, MpaDao mpaDao) {
        this.filmStorage = filmStorage;
        this.genreDao = genreDao;
        this.likeDao = likeDao;
        this.mpaDao = mpaDao;
    }


    @Override
    public List<Film> getAll() {
        List<Film> films = filmStorage.getAll();
        for (Film film : films) {
            film.setGenres(genreDao.getGenres((int) (long) film.getId()));
        }
        return films;
    }

    @Override
    public Film getById(Long id) {
        Film film = filmStorage.getById(id);
        if (film == null) {
            throw new NotFoundException("Фильм не найден");
        } else {
            film.setGenres(genreDao.getGenres((int) (long) film.getId()));
            return film;
        }
    }

    @Override
    public Film create(Film film) {
        if (film.getReleaseDate().isBefore(checkDate)) {
            throw new ValidationException("Указанна неверная дата!");
        } else if (film.getGenres() == null) {
            return filmStorage.create(film);
        } else {
            Film film1 = filmStorage.create(film);
            genreDao.setGenres(film.getGenres(), film1.getId());
            return film1;
        }
    }

    @Override
    public Film update(Film film) {
        if (film.getId() <= 0 || !filmStorage.getAll().contains(film)) {
            throw new NotFoundException("Фильм не найден!");
        } else if (film.getReleaseDate().isBefore(checkDate)) {
            throw new ValidationException("Указанна неверная дата!");
        } else if (film.getGenres() == null) {
            return filmStorage.update(film);
        } else {
            genreDao.setGenres(film.getGenres(), film.getId());
            return filmStorage.update(film);
        }
    }

    public void addLike(Long idFilm, Long idUser) {
        if (idFilm <= 0 || idUser <= 0) {
            throw new NotFoundException("Id указан неверно!");
        } else {
            likeDao.addLike(idFilm, idUser);
        }
    }

    public void deleteLike(Long idFilm, Long idUser) {
        if (idFilm <= 0 || idUser <= 0) {
            throw new NotFoundException("Id указан неверно!");
        } else {
            likeDao.deleteLike(idFilm, idUser);
        }
    }

    public List<Film> getTopFilm(Integer count) {
        Set<Film> topFilm = likeDao.getTopFilm(count).stream()
                .map(Integer -> getById(Long.valueOf(Integer)))
                .collect(Collectors.toSet());
        List<Film> allFilm = getAll();
        Set<Film> result = Stream.concat(topFilm.stream(), allFilm.stream())
                .limit(count)
                .collect(Collectors.toSet());
        return new ArrayList<>(result);
    }

    public List<Genre> getAllGenres() {
        return genreDao.getAllGenres();
    }

    public Genre getGenreById(Integer id) {
        if (id <= 0) {
            throw new NotFoundException("id not found!");
        }
        return genreDao.getGenreById(id);
    }

    public List<Mpa> getAllMpa() {
        return mpaDao.getAllMpa();
    }

    public Mpa getMpaById(Integer id) {
        if (id <= 0) {
            throw new NotFoundException("id not found!");
        }
        return mpaDao.getMpa(id);
    }
}

