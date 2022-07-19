package ru.yandex.practicum.filmorate.models;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class User {
    private Set<Long> friends = new HashSet<>();
    private String name;
    private Long id;
    @NonNull
    @NotEmpty
    @Email
    private final String email;
    @Pattern(regexp = "^\\S*$")
    private final String login;
    @Past
    private final LocalDate birthday;

}
