package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FriendsDao;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Service
public class UserService implements BaseService<User> {
    UserStorage userStorage;
    FriendsDao friendsDao;

    public UserService(UserStorage userStorage, FriendsDao friendsDao) {
        this.userStorage = userStorage;
        this.friendsDao = friendsDao;
    }

    @Override
    public User create(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        return userStorage.create(user);
    }

    @Override
    public Collection<User> getAll() {
        return userStorage.getAll();
    }

    @Override
    public User update(User user) {
        if (user.getId() <= 0) {
            throw new NotFoundException("Incorrect ID!");
        } else {
            User user1 = userStorage.update(user);
            if (user1 == null) {
                throw new NotFoundException("User not found!");
            }
            return user1;

        }
    }

    @Override
    public User getById(Long id) {
        if (id <= 0) {
            throw new NotFoundException("Incorrect ID!");
        } else {
            User user1 = userStorage.getById(id);
            if (user1 == null) {
                throw new NotFoundException("User not found!");
            }
            return user1;
        }
    }

    public void addFriend(Long id1, Long id2) {
        if (id1 <= 0 || id2 <= 0) {
            throw new NotFoundException("Incorrect ID!");
        } else {
            friendsDao.addFriend(id1, id2);
        }
    }

    public void deleteFriend(Long id1, Long id2) {
        if (id1 <= 0 || id2 <= 0) {
            throw new NotFoundException("Incorrect ID!");
        } else {
            friendsDao.deleteFriend(id1, id2);
        }
    }

    public Collection<User> getFriends(Long id) {
        if (id <= 0) {
            throw new NotFoundException("Incorrect ID!");
        } else {
            return friendsDao.getFriends(id);
        }
    }

    public Collection<User> getCommonFriend(Long id1, Long id2) {
        if (id1 <= 0 || id2 <= 0) {
            throw new NotFoundException("Incorrect ID!");
        } else {
            return friendsDao.getCommonFriends(id1, id2);
        }
    }
}
