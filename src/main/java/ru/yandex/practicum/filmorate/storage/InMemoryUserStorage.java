package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.models.User;

import java.util.HashMap;

@Component
public class InMemoryUserStorage implements UserStorage {
    private Long id = 0L;
    private HashMap<Long, User> storage = new HashMap<>();

    @Override
    public HashMap<Long, User> getAll() {
        return storage;
    }

    @Override
    public User getById(Long id) {
        return storage.get(id);
    }

    @Override
    public User create(User user) {
        user.setId(++id);
        storage.put(id, user);
        return user;
    }

    @Override
    public User update(User user) {
        storage.put(user.getId(), user);
        return user;
    }

    public void deleteAll() {
        storage.clear();
    }
}
