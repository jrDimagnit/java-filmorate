package ru.yandex.practicum.filmorate.models;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
public class Film {
    private Long id;
    private Mpa mpa;
    private Set<Genre> genres;
    @NotBlank
    private final String name;
    @Size(max = 200)
    private final String description;
    private final LocalDate releaseDate;
    @Positive
    private final int duration;
}
