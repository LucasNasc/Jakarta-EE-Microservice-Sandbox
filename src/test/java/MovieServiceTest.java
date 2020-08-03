import br.com.lucas.dto.MovieDTO;
import br.com.lucas.entity.MovieEntity;
import br.com.lucas.exceptions.BusinessException;
import br.com.lucas.repositories.MovieRepository;
import br.com.lucas.services.MovieServices;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MovieDTO.class)
public class MovieServiceTest {

    @InjectMocks
    MovieServices services;

    @Mock
    MovieRepository repository;

    MovieEntity entity = new MovieEntity();

    @Before
    @PowerMockIgnore
    public void begin(){
        MockitoAnnotations.initMocks(this);

        entity.setId(1L);
        entity.setTitle("Era");
        entity.setDirector("es");
        entity.setReleaseDate(LocalDate.now());
    }

    @Test
    @DisplayName("Deve testar Listagem de todos os filmes")
    public void listAllTest() {

        PowerMockito.mockStatic(MovieDTO.class);
        List<MovieEntity> listEntity = new ArrayList<>();
        MovieEntity entity = new MovieEntity();

        entity.setId(1L);
        entity.setTitle("Era Uma Vez");
        entity.setDirector("Geroge");
        entity.setReleaseDate(LocalDate.now());

        listEntity.add(entity);

        MovieDTO as = new MovieDTO();
        as.setId(1L);
        as.setTitle("Era Uma Vez");
        as.setDirector("Geroge");
        as.setReleaseDate(LocalDate.now().toString());

        Mockito.when(MovieDTO.toDTO(entity)).thenReturn(as);

        Mockito.when(repository.listAll()).thenReturn(listEntity);

        List<MovieDTO> a = services.listAll();
        assertTrue( a.size() > 0 );

    }

    @Test
    @DisplayName("Deve testar a persitencia de um novo filme no banco de dados")
    public void saveEntity() {

        MovieDTO entity = new MovieDTO();
        entity.setId(1L);
        entity.setTitle("Era");
        entity.setDirector("es");
        entity.setReleaseDate("20/12/2017");
        services.save(entity);

        assertNotNull(entity);
    }

    @Test
    @DisplayName("Deve Testar a verificaçao de duplicidade de um filme pelo nome")
    public void verifyDuoTest() {

        MovieDTO entity = new MovieDTO();
        entity.setId(1L);
        entity.setTitle("Era");
        entity.setDirector("es");
        entity.setReleaseDate("20/12/2017");

       Boolean a =  services.duoVerify(entity);

       assertEquals(false, a);
    }

    @Test
    @DisplayName("Deve Testar um delete de um filme no banco")
    public void deleteTest() throws BusinessException {

        Mockito.when(this.repository.findById(entity.getId())).thenReturn(Optional.of(entity));

        services.delete(entity.getId());

        assertNotNull(entity);
    }

    @Test
    @DisplayName(" Deve testar a atualizaçao de um filme no banco de dados")
    public void updateTest() throws BusinessException {

        MovieEntity entity = new MovieEntity();
        entity.setId(1L);
        entity.setDirector("LUCAS");
        entity.setReleaseDate(LocalDate.now());
        entity.setTitle("Star Wars V");

        MovieDTO dto = new MovieDTO();
        dto.setId(1L);
        dto.setDirector("George Lucas");
        dto.setReleaseDate("20/12/2020");
        dto.setTitle("Star Wars III");

        Mockito.when(this.repository.findById(entity.getId())).thenReturn(Optional.of(entity));

        MovieDTO response = services.update(dto);

        assertNotNull(response);
        assertEquals(response, dto);

    }
    @Test
    @DisplayName("Deve testar a busca de um filme pelo id")
    public void findByIdTest() throws BusinessException {

        MovieEntity entity = new MovieEntity();
        entity.setId(1L);
        entity.setDirector("LUCAS");
        entity.setReleaseDate(LocalDate.now());
        entity.setTitle("Star Wars V");


        MovieDTO dto = new MovieDTO();
        dto.setId(1L);
        dto.setDirector("LUCAS");
        dto.setReleaseDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        dto.setTitle("Star Wars V");

        Mockito.when(this.repository.findById(entity.getId())).thenReturn(Optional.of(entity));

        MovieDTO response = services.findById(dto.getId());

        assertNotNull(response);
        assertEquals(response, dto);

    }

}
