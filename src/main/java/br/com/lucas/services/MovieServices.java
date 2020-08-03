package br.com.lucas.services;
import br.com.lucas.dto.MovieDTO;
import br.com.lucas.entity.MovieEntity;
import br.com.lucas.exceptions.BusinessException;
import br.com.lucas.repositories.MovieRepository;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class MovieServices {

    @Inject
    MovieRepository repository;

    @Transactional
    @Timed(name= "ProcessingListAll",
            description = "Time needed to process the List of Movies",
            absolute = true,
            unit = MetricUnits.MILLISECONDS)
    @Counted(name = "Counter of List All",
            absolute = true,
            description = "Number of times listAll of system method is requested")
    public List<MovieDTO> listAll() {

        List<MovieDTO> dtos = new ArrayList<>();

        repository.listAll().forEach(movie -> dtos.add(MovieDTO.toDTO(movie)));

        return dtos;
    }

    @Transactional
    @Timed(name= "ProcessingSaveMovie",
            description = "Time needed to save a new movie",
            absolute = true,
            unit = MetricUnits.MILLISECONDS)
    @Counted(name = "Counter Save Movie",
            absolute = true,
            description = "Number of times Save movie")
    public void save(MovieDTO dto) {

        this.repository.save(MovieEntity.toEntity(dto));

    }

    @Transactional
    @Timed(name= "ProcessingDeleteMovie",
            description = "Time needed to delete a new movie",
            absolute = true,
            unit = MetricUnits.MILLISECONDS)
    @Counted(name = "Counter delete Movie",
            absolute = true,
            description = "Number of times delete a movie")
    public void delete(Long id) throws BusinessException {

        MovieEntity entity =  this.repository.findById(id).orElseThrow(() -> new BusinessException("Movie not Found"));

        this.repository.delete(entity);

    }

    @Transactional
    @Timed(name= "ProcessingUpdateMovie",
            description = "Time needed to update a movie",
            absolute = true,
            unit = MetricUnits.MILLISECONDS)
    @Counted(name = "Counter to update Movie",
            absolute = true,
            description = "Number of times update movie")
    public MovieDTO update(MovieDTO dto) throws BusinessException {


        MovieEntity entity =  this.repository
                .findById(dto.getId()).orElseThrow(() -> new BusinessException("Movie not Found"));

        if (Objects.nonNull(dto.getDirector())) {
            entity.setDirector(dto.getDirector());
        }
        if(Objects.nonNull(dto.getTitle())) {
            entity.setTitle(dto.getTitle());
        }
        if(Objects.nonNull(dto.getReleaseDate())) {
            entity.setReleaseDate(LocalDate.parse(dto.getReleaseDate() , DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }

        this.repository.update(entity);

        return MovieDTO.toDTO(entity);
    }

    @Transactional
    @Timed(name= "ProcessingfindByID",
            description = "Time needed to search movie by id",
            absolute = true,
            unit = MetricUnits.MILLISECONDS)
    @Counted(name = "Counter Save Movie",
            absolute = true,
            description = "Number of times find movie by id")
    public MovieDTO findById(Long id) throws BusinessException {

        return MovieDTO.toDTO(this.repository.findById(id)
                .orElseThrow( () -> new BusinessException(" Movie not Found ")));

    }

    @Transactional
    @Timed(name= "ProcessingfindByNome",
            description = "Time needed to search movie by name",
            absolute = true,
            unit = MetricUnits.MILLISECONDS)
    @Counted(name = "Counter Findbyname method",
            absolute = true,
            description = "Number of times find movie by name")
    public boolean duoVerify(MovieDTO dto) {

        return this.repository.findByNome(MovieEntity.toEntity(dto)).isPresent();

    }
}
