package academy.lima.catalog.repository;

import academy.lima.catalog.domain.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoviesRepository extends JpaRepository<Movies, Long> {

    List<Movies> findByName(String name);

}
