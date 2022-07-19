package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.User;

import java.util.HashMap;

public interface UserStorage {
    HashMap<Long, User> getAll();

    User getById(Long id);

    User create(User user);

    User update(User user);

    void deleteAll();
}
