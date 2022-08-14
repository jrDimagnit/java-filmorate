package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class LikeDao {

    private final JdbcTemplate jdbcTemplate;

    public LikeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Integer> getTopFilm(Integer count) {
        String sqlQuery = "select FILM_ID,COUNT(USER_ID) from film_likes group by FILM_ID" +
                "                order by COUNT(USER_ID) DESC limit ?";
        List<Integer> filmsLike = jdbcTemplate.queryForStream(sqlQuery, (resultSet, rowNum) -> {
                    return new Integer(resultSet.getInt(1));
                }, count)
                .collect(Collectors.toList());
        return filmsLike;
    }

    public void addLike(Long filmId, Long userId) {
        String sqlQuery = "insert into film_likes(FILM_ID, USER_ID) values(?, ?)";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        String sqlQuery = "delete from film_likes where FILM_ID = ? and USER_ID = ?";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }
}
