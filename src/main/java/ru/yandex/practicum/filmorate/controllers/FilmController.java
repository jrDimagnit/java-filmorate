package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
public class FilmController {
    private int idFilm;
    private LocalDate dateBefore = LocalDate.of(1895, 12, 28);
    private HashMap<Integer, Film> films = new HashMap<>();

    @GetMapping("/films")
    public List<Film> getAllFilm() {
        List<Film> reqList = new ArrayList<>();
        for (Film film : films.values()) {
            reqList.add(film);
        }
        return reqList;
    }

    @PostMapping("/films")
    public Film createFilm(@RequestBody Film film) {
        if (film.getName().isEmpty()) {
            throw new ValidationException("Название фильма отсутствует!");
        } else if (film.getReleaseDate().isBefore(dateBefore)) {
            throw new ValidationException("Указанна неверная дата!");
        } else if (film.getDescription().length() > 200) {
            throw new ValidationException("Описание не должно превышать 200 символов!");
        } else if (film.getDuration() < 0) {
            throw new ValidationException("Продолжительность не может быть отрицательной!");
        } else {
            ++idFilm;
            film.setId(idFilm);
            films.put(idFilm, film);
            return film;
        }
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) {
        if (film.getName().isEmpty()) {
            throw new ValidationException("Название фильма отсутствует!");
        } else if (film.getReleaseDate().isBefore(dateBefore)) {
            throw new ValidationException("Указанна неверная дата!");
        } else if (film.getDescription().length() > 200) {
            throw new ValidationException("Описание не должно превышать 200 символов!");
        } else if (film.getDuration() < 0) {
            throw new ValidationException("Продолжительность не может быть отрицательной!");
        } else if (!films.containsKey(film.getId())) {
            throw new ValidationException("Фильма с таким id не найдено!");
        } else {
            films.put(film.getId(), film);
            return film;
        }
    }
}
