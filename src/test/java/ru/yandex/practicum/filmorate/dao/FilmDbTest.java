package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Mpa;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbTest {
    private final FilmDbStorage filmDbStorage;


    @Test
    public void filmStorageTest() {
        Film newFilm = new Film("newFilm", "description",
                LocalDate.of(2000, 10, 13), 100);
        Mpa mpa = new Mpa();
        mpa.setId(1);
        newFilm.setMpa(mpa);

        Optional<Film> film1 = Optional.of(filmDbStorage.create(newFilm));
        assertThat(film1)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("name", "newFilm")
                );

        Optional<Film> getFilm = Optional.of(filmDbStorage.getById(1l));
        assertThat(getFilm)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("name", "newFilm")
                );
        Film updateFilm = new Film("update", "newdes",
                LocalDate.of(2000, 10, 13), 100);
        Mpa mpa1 = new Mpa();
        mpa1.setId(2);
        updateFilm.setMpa(mpa1);
        updateFilm.setId(1l);
        Optional<Film> film1Update = Optional.of(filmDbStorage.update(updateFilm));
        assertThat(film1Update)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("name", "update"));

    }

}
