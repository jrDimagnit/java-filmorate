package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.Mpa;


import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmMapper implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film(rs.getString("name"), rs.getString("description"),
                rs.getDate("release_date").toLocalDate(), rs.getInt("duration"));
        film.setMpa(new Mpa(rs.getInt("rating_id"), rs.getString("mpa_name")));
        film.setId(Long.valueOf(rs.getInt("FILM_ID")));
        return film;
    }
}
