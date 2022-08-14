package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.models.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class MpaDao {
    private final JdbcTemplate jdbcTemplate;

    public MpaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Mpa getMpa(Integer id) {
        String sql = "select * from mpa where mpa_id = ?";
        return jdbcTemplate.queryForObject(sql, this::mapMpaRow, id);
    }

    public List<Mpa> getAllMpa() {
        String sql = "select * from mpa";
        return jdbcTemplate.query(sql, this::mapMpaRow);
    }

    public Mpa mapMpaRow(ResultSet rs, int rowNum) throws SQLException {
        Mpa mpa = new Mpa(rs.getInt("mpa_id"),
                rs.getString("mpa_name"));
        return mpa;
    }
}
