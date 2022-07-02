package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class User {
    private Integer id;
    private String name;
    @NonNull
    @NotEmpty
    @Email
    private final String email;
    @Pattern(regexp ="^\\S*$")
    private final String login;
    @Past
    private final LocalDate birthday;

}
