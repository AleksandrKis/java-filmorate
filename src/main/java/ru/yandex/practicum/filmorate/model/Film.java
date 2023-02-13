package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Slf4j
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Film {
    @EqualsAndHashCode.Include
    int id;
    @NotBlank
    private String name;
    @Size(min = 1,max = 200)
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @Min(1)
    private long duration;

}
