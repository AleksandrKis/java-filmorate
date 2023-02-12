package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    @NotBlank
    private String name;
    @Size(min = 1,max = 200)
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @Min(1)
    private long duration;
}
