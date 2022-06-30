package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
public class UserController {
    private int idUser;
    private HashMap<Integer, User> users = new HashMap<>();

    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User> reqList = new ArrayList<>();
        for (User user : users.values()) {
            reqList.add(user);
        }
        return reqList;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        if (user.getLogin().contains(" ") || user.getLogin().isEmpty()) {
            throw new ValidationException("Логин не может содержать пробелы!");
        } else if (!user.getEmail().contains("@")) {
            throw new ValidationException("Email не соответствует!");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем!");
        } else {
            ++idUser;
            if (user.getName() == null || user.getName().isEmpty()) {
                user.setName(user.getLogin());
            }
            user.setId(idUser);
            users.put(idUser, user);
            return user;
        }
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может содержать пробелы!");
        } else if (!users.containsKey(user.getId())) {
            throw new ValidationException("Пользователь с данным id не найден!");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем!");
        } else if (user.getEmail().isEmpty() && !user.getEmail().contains("@")) {
            throw new ValidationException("Неправильно указан email!");
        } else {
            users.put(user.getId(), user);
            return user;
        }
    }
}
