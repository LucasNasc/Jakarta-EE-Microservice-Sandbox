import br.com.lucas.dto.MovieDTO;
import br.com.lucas.entity.MovieEntity;
import org.junit.Test;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MovieStaticTest {

    @Test
    public void toDTOTest() {

        MovieEntity entity = new MovieEntity();

        entity.setId(1L);
        entity.setTitle("Era Uma Vez");
        entity.setDirector("Geroge");
        entity.setReleaseDate(LocalDate.now());

        MovieDTO as = new MovieDTO();
        as.setId(1L);
        as.setTitle("Era Uma Vez");
        as.setDirector("Geroge");
        as.setReleaseDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        MovieDTO dto = MovieDTO.toDTO(entity);

        assertNotNull(dto);
        assertEquals(as, dto);
    }

    @Test
    public void toEntityTest() {

        MovieEntity entity = new MovieEntity();

        entity.setId(1L);
        entity.setTitle("Era Uma Vez");
        entity.setDirector("Geroge");
        entity.setReleaseDate(LocalDate.now());

        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(1L);
        movieDTO.setTitle("Era Uma Vez");
        movieDTO.setDirector("Geroge");
        movieDTO.setReleaseDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        MovieEntity movieEntity = MovieEntity.toEntity(movieDTO);

        assertNotNull(movieEntity);
        assertEquals(entity, movieEntity);
    }


}
