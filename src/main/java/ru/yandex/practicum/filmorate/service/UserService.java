package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements BaseService<User> {
    UserStorage userStorage;

    UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
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
        return userStorage.getAll().values();
    }

    @Override
    public User update(User user) {
        if (user.getId() <= 0 || !userStorage.getAll().containsKey(user.getId())) {
            throw new NotFoundException("Пользователь не найден!");
        } else {
            return userStorage.update(user);
        }
    }

    @Override
    public User getById(Long id) {
        if (id <= 0 || !userStorage.getAll().containsKey(id)) {
            throw new NotFoundException("Пользователь не найден!");
        } else {
            return userStorage.getById(id);
        }
    }

    public void deleteAll() {
        userStorage.deleteAll();
    }

    public void addFriend(Long id1, Long id2) {
        if (id1 <= 0 || id2 <= 0 || !(userStorage.getAll().containsKey(id1) || userStorage.getAll().containsKey(id2))) {
            throw new NotFoundException("Пользователь не найден!");
        } else {
            userStorage.getAll().get(id1).getFriends().add(id2);
            userStorage.getAll().get(id2).getFriends().add(id1);
        }
    }

    public void deleteFriend(Long id1, Long id2) {
        if (id1 <= 0 || id2 <= 0 || !(userStorage.getAll().containsKey(id1) || userStorage.getAll().containsKey(id2))) {
            throw new NotFoundException("Пользователь не найден!");
        } else {
            userStorage.getAll().get(id1).getFriends().remove(id2);
            userStorage.getAll().get(id1).getFriends().remove(id2);
        }
    }

    public Collection<User> getFriends(Long id) {
        if (id <= 0 || !userStorage.getAll().containsKey(id)) {
            throw new NotFoundException("Пользователь не найден!");
        } else {
            return userStorage.getAll().get(id).getFriends().stream()
                    .map((Long) -> userStorage.getAll().get(Long))
                    .collect(Collectors.toList());
        }
    }

    public Collection<User> getCommonFriend(Long id1, Long id2) {
        if (id1 <= 0 || id2 <= 0 || !(userStorage.getAll().containsKey(id1) || userStorage.getAll().containsKey(id2))) {
            throw new NotFoundException("Пользователь не найден!");
        } else {
            return userStorage.getCommonFriend(id1, id2);
        }
    }
}
