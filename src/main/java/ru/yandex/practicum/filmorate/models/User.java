package ru.yandex.practicum.filmorate.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class User {
    private String name;
    private Long id;
    @NotBlank
    @Email
    private final String email;
    @Pattern(regexp = "^\\S*$")
    private final String login;
    @Past
    private final LocalDate birthday;
}
