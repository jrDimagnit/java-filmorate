package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class Film {
    private Integer id;
    @NonNull
    @NotEmpty
    private final String name;
    @Size(max = 200)
    private final String description;
    private final LocalDate releaseDate;
    @Positive
    private final int duration;

}
