package br.com.lucas.entity;
import br.com.lucas.dto.MovieDTO;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Table(name = "movie")
public class MovieEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "director")
    private String director;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public static MovieEntity toEntity(MovieDTO dto) {
        return MovieEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .director(dto.getDirector())
                .releaseDate(LocalDate.parse(dto.getReleaseDate() , DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieEntity entity = (MovieEntity) o;
        return Objects.equals(id, entity.id) &&
                Objects.equals(title, entity.title) &&
                Objects.equals(director, entity.director) &&
                Objects.equals(releaseDate, entity.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, director, releaseDate);
    }
}
