package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class User {
    private Integer id;
    private String name;
    @NonNull
    private final String email;
    private final String login;
    private final LocalDate birthday;

}
