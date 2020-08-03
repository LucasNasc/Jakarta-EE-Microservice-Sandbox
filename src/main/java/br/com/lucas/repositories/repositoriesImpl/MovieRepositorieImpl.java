package br.com.lucas.repositories.repositoriesImpl;

import br.com.lucas.config.ConnectionFactory;
import br.com.lucas.entity.MovieEntity;
import br.com.lucas.repositories.MovieRepository;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MovieRepositorieImpl implements MovieRepository {

    private EntityManager em;

    @PostConstruct
    public void init() {

        em = ConnectionFactory.getEntityManager();

    }

    @Override
    public List<MovieEntity> listAll() {

        List<MovieEntity> movies = em.createQuery(" SELECT p FROM MovieEntity p", MovieEntity.class).getResultList();

        return movies;

    }

    @Override
    public Optional<MovieEntity> findById(Long id) {

        MovieEntity entity = em.find(MovieEntity.class, id);

        return Objects.nonNull(entity) ? Optional.of(entity) : Optional.empty();

    }

    @Override
    public void delete(MovieEntity entity) {

        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();

    }

    @Override
    public void save(MovieEntity entity) {

        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();

    }

    @Override
    public void update(MovieEntity entity) {

        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();

    }

    @Override
    public Optional<MovieEntity> findByNome(MovieEntity entity) {

        try {

            MovieEntity consulta = em.createQuery("select p from MovieEntity p where p.title = :title ", MovieEntity.class)
                    .setParameter("title", entity.getTitle())
                    .getSingleResult();

            return Optional.of(consulta);

        } catch (NoResultException e) {

            return Optional.empty();

        }

    }


}
