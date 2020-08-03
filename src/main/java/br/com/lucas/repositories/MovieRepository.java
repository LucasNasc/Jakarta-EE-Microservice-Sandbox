package br.com.lucas.repositories;

import br.com.lucas.entity.MovieEntity;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {

    List<MovieEntity> listAll();

    Optional<MovieEntity> findById(Long id);

    void delete(MovieEntity entity);

    void save(MovieEntity entity);

    void update(MovieEntity entity);

    Optional<MovieEntity> findByNome(MovieEntity entity);

}
