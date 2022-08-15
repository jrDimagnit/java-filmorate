package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

@Component
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Film> getAll() {
        String sql = "select * from films join mpa on (rating_id = mpa_id)";
        return jdbcTemplate.query(sql, new FilmMapper());
    }

    @Override
    public Film getById(Long id) {
        String sql = "select * from films join mpa on (rating_id = mpa_id) where film_id = ?";
        return jdbcTemplate.query(sql, new FilmMapper(), id).stream().findAny().orElse(null);
    }

    @Override
    public Film create(Film film) {
        String sqlQuery = "insert into films (NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATING_ID) " +
                "values (?, ?, ?, ?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"FILM_ID"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            final LocalDate birthday = film.getReleaseDate();
            if (birthday == null) {
                stmt.setNull(3, Types.DATE);
            } else {
                stmt.setDate(3, Date.valueOf(birthday));
            }
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        film.setId(keyHolder.getKey().longValue());
        return film;
    }

    @Override
    public Film update(Film film) {
        String sqlQuery = "UPDATE films SET NAME = ?, DESCRIPTION = ?," +
                " RELEASE_DATE = ?, DURATION = ?, RATING_ID = ?  where FILM_ID = ?";
        jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(),
                Date.valueOf(film.getReleaseDate()), film.getDuration(), film.getMpa().getId(), film.getId());
        return film;

    }

}
