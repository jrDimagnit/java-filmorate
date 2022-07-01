package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controllers.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;

@Slf4j
@RestController
public class FilmController extends Controller<Film> {

    private final LocalDate checkDate = LocalDate.of(1895, 12, 28);

    @Override
    @GetMapping("/films")
    public Collection<Film> getAll() {
        return super.getAll();
    }

    @Override
    @PostMapping("/films")
    public Film create(@Valid @RequestBody Film film) {
        if (film.getReleaseDate().isBefore(checkDate)) {
            throw new ValidationException("Указанна неверная дата!");
        } else {
            film.setId(++id);
            base.put(id, film);
            return film;
        }
    }

    @Override
    @PutMapping("/films")
    public Film update(@Valid @RequestBody Film film) {
        if (!base.containsKey(film.getId())) {
            throw new ValidationException("Фильма с таким id не найден!");
        } else if (film.getReleaseDate().isBefore(checkDate)) {
            throw new ValidationException("Указанна неверная дата!");
        } else {
            base.put(film.getId(), film);
            return film;
        }
    }
    @Override
    @DeleteMapping("/films")
    public void deleteAll(){
        base.clear();
    }
}
