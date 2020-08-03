import br.com.lucas.entity.MovieEntity;
import br.com.lucas.repositories.repositoriesImpl.MovieRepositorieImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MovieResourceTest {

    @InjectMocks
    MovieRepositorieImpl resource;

    @Mock
    EntityManager entityManager;

    @Before
    public void begin() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Deve Testar o repository findbyId")
    public void findByIdRepositoryTest() {

        MovieEntity entityMock = new MovieEntity();

        entityMock.setTitle("aaa");
        entityMock.setId(1L);
        entityMock.setReleaseDate(LocalDate.now());
        entityMock.setDirector("nilson");


        when(entityManager.find(MovieEntity.class, 1L)).thenReturn(entityMock);

        Optional<MovieEntity> entity = resource.findById(1L);

        assertNotNull(entity);
        assertEquals(Optional.of(entityMock), entity);
    }

    @Test
    @DisplayName("Deve Testar o repository findbyId")
    public void listAllrepositoryTest() {
        List<MovieEntity> movieEntityList = new ArrayList<>();

        MovieEntity entityMock = new MovieEntity();

        entityMock.setTitle("aaa");
        entityMock.setId(1L);
        entityMock.setReleaseDate(LocalDate.now());
        entityMock.setDirector("nilson");

        movieEntityList.add(entityMock);

        TypedQuery<MovieEntity> query = (TypedQuery<MovieEntity>) mock(TypedQuery.class);

        when(entityManager.createQuery(" SELECT p FROM MovieEntity p", MovieEntity.class)).thenReturn(query);

        when(query.getResultList()).thenReturn(movieEntityList);

        List<MovieEntity> response = resource.listAll();

        assertNotNull(response);
        assertEquals(movieEntityList, response);
    }

}
