package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Component
public class GenreDao {
    private final JdbcTemplate jdbcTemplate;

    public GenreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Genre mapGenreRow(ResultSet rs, int rowNum) throws SQLException {
        Genre genre = new Genre(rs.getInt("genre_id"),
                rs.getString("name"));
        return genre;
    }

    public Set<Genre> getGenres(Integer id) {
        String sql = "select genre_id, name from genres  join film_genres  ON (genres.genre_id = film_genres.ID_GENRE) where film_id = ?";
        return new LinkedHashSet<>(jdbcTemplate.query(sql, this::mapGenreRow, id));
    }

    public void setGenres(Set<Genre> genres, Long id) {
        String clearTable = "delete from film_genres where film_id = ?";
        jdbcTemplate.update(clearTable, id);
        String sqlQuery = "insert into film_genres(FILM_ID, ID_GENRE) values(?, ?)";
        for (Genre genre : genres)
            jdbcTemplate.update(sqlQuery, id, genre.getId());
    }

    public List<Genre> getAllGenres() {
        String sql = "select * from genres";
        return jdbcTemplate.query(sql, this::mapGenreRow);
    }

    public Genre getGenreById(Integer id) {
        String sql = "select * from genres where GENRE_ID = ?";
        return jdbcTemplate.queryForObject(sql, this::mapGenreRow, id);
    }

}
