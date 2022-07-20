package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.models.User;

import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public void deleteAll() {
        storage.clear();
    }
    @Override
    public Collection<User> getCommonFriend(Long id1, Long id2){
        List<Long> commonList = new ArrayList<>(getAll().get(id1).getFriends());
        commonList.addAll(getAll().get(id2).getFriends());
        Set<Long> checkedId = new HashSet<>();
        return commonList.stream()
                .filter(e -> !checkedId.add(e))
                .map((Long) -> getAll().get(Long))
                .collect(Collectors.toList());
    }
}
