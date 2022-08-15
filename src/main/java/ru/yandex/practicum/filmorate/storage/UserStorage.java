package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.models.User;

import java.util.List;

public interface UserStorage {
    List<User> getAll();

    User getById(Long id);

    User create(User user);

    User update(User user);

}
