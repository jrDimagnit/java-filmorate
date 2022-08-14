package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.models.User;

import java.util.List;

@Component
public class FriendsDao {

    JdbcTemplate jdbcTemplate;

    public FriendsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addFriend(Long idUser, Long idFriend) {
        String sql = "insert into friends (USER_ID, FRIEND_ID) values(?, ?)";
        jdbcTemplate.update(sql, idUser, idFriend);
    }

    public void deleteFriend(Long idUser, Long idFriend) {
        String sql = "DELETE from friends where USER_ID = ? and FRIEND_ID = ?";
        jdbcTemplate.update(sql, idUser, idFriend);
    }

    public List<User> getFriends(Long id) {
        String sql = "select * from users join friends f on users.USER_ID = f.FRIEND_ID where f.USER_ID = ?";
        return jdbcTemplate.query(sql, new UserMapper(), id);
    }

    public List<User> getCommonFriends(Long id, Long id1) {
        String sql = "select u.* from users u join friends f1 on u.USER_ID = f1.FRIEND_ID " +
                "join friends f2 on u.USER_ID = f2.FRIEND_ID where f1.USER_ID = ? AND f2.USER_ID = ?";
        return jdbcTemplate.query(sql, new UserMapper(), id, id1);
    }
}
