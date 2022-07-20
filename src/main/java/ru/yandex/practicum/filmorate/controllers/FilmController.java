package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    FilmService filmService;

    FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping()
    public Collection<Film> getAllFilms() {
        return filmService.getAll();
    }

    @GetMapping("/{filmId}")
    public Film getById(@PathVariable Long filmId) {
        return filmService.getById(filmId);
    }

    @GetMapping("/popular")
    public Set<Film> getTopFilm(@RequestParam(required = false) Integer count) {
        if (count == null) {
            return filmService.getTopFilm(10);
        } else {
            return filmService.getTopFilm(count);
        }
    }

    @PostMapping()
    public Film create(@Valid @RequestBody Film film) {
        return filmService.create(film);
    }

    @PutMapping()
    public Film update(@Valid @RequestBody Film film) {
        return filmService.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addFilmLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteFilmLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.deleteLike(id, userId);
    }


    @DeleteMapping()
    public void deleteAll() {
        filmService.deleteAll();
    }
}
