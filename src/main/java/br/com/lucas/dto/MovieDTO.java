package br.com.lucas.dto;


import br.com.lucas.entity.MovieEntity;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MovieDTO implements Serializable {

    private Long id;

    private String title;

    private String director;



    private String releaseDate;

    public static MovieDTO toDTO(MovieEntity entity) {
        return MovieDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .director(entity.getDirector())
                .releaseDate(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(entity.getReleaseDate()))
                .build();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDTO movieDTO = (MovieDTO) o;
        return Objects.equals(id, movieDTO.id) &&
                Objects.equals(title, movieDTO.title) &&
                Objects.equals(director, movieDTO.director) &&
                Objects.equals(releaseDate, movieDTO.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, director, releaseDate);
    }
}
