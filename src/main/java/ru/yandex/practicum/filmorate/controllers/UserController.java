package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controllers.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;


import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
public class UserController extends Controller<User> {

    @Override
    @GetMapping("/users")
    public Collection<User> getAll() {
        return super.getAll();
    }

    @Override
    @PostMapping("/users")
    public User create(@Valid @RequestBody User user) {
        if (user.getLogin().contains(" ") || user.getLogin().isEmpty()) {
            throw new ValidationException("Логин не может содержать пробелы!");
        } else {
            if (user.getName() == null || user.getName().isEmpty()) {
                user.setName(user.getLogin());
            }
            user.setId(++id);
            base.put(id, user);
            return user;
        }
    }

    @Override
    @PutMapping("/users")
    public User update(@Valid @RequestBody User user) {
        if (!base.containsKey(user.getId())) {
            throw new ValidationException("Пользователь с данным id не найден!");
        } else {
            base.put(user.getId(), user);
            return user;
        }
    }

    @Override
    @DeleteMapping("/users")
    public void deleteAll() {
        base.clear();
    }
}
