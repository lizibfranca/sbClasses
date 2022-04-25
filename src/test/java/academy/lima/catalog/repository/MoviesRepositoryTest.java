package academy.lima.catalog.repository;

import academy.lima.catalog.domain.Movies;
import academy.lima.catalog.util.MovieCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Movie Repository")
@Log4j2
class MoviesRepositoryTest {

    @Autowired
    private MoviesRepository moviesRepository;

    @Test
    @DisplayName("Save persists movie when Successful")
    void save_PersistMovie_WhenSuccessful(){
        Movies movieToBeSaved = MovieCreator.createMovieToBeSaved();

        Movies movieSaved = this.moviesRepository.save(movieToBeSaved);

        Assertions.assertThat(movieSaved).isNotNull();

        Assertions.assertThat(movieSaved.getId()).isNotNull();

        Assertions.assertThat(movieSaved.getName()).isNotEmpty();

        Assertions.assertThat(movieSaved.getName()).isEqualTo(movieToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates movie when Successful")
    void save_UpdatesMovies_WhenSuccessful(){
        Movies movieToBeSaved = MovieCreator.createMovieToBeSaved();

        Movies movieSaved = this.moviesRepository.save(movieToBeSaved);

        movieSaved.setName("Titanic");

        Movies movieUpdated = this.moviesRepository.save(movieSaved);

        Assertions.assertThat(movieUpdated).isNotNull();

        Assertions.assertThat(movieUpdated.getId()).isNotNull();

        Assertions.assertThat(movieUpdated.getName()).isEqualTo(movieSaved.getName());
    }

    @Test
    @DisplayName("Delete removes movie when Successful")
    void delete_RemovesMovies_WhenSuccessful(){
        Movies movieToBeSaved = MovieCreator.createMovieToBeSaved();

        Movies movieSaved = this.moviesRepository.save(movieToBeSaved);

        this.moviesRepository.delete(movieSaved);

        Optional<Movies> movieOptional = this.moviesRepository.findById(movieSaved.getId());

        Assertions.assertThat(movieOptional).isEmpty();

    }

    @Test
    @DisplayName("Find By Name returns list of movies when Successful")
    void findByName_ReturnsListOfMovies_WhenSuccessful(){
        Movies movieToBeSaved = MovieCreator.createMovieToBeSaved();

        Movies movieSaved = this.moviesRepository.save(movieToBeSaved);

        String name = movieSaved.getName();

        List<Movies> movies = this.moviesRepository.findByName(name);

        Assertions.assertThat(movies).isNotEmpty();

        Assertions.assertThat(movies).contains(movieSaved);

    }

    @Test
    @DisplayName("Find By Name returns empty list when no movie is found")
    void findByName_ReturnsEmptyList_WhenMoviesIsNotFound(){
        List<Movies> movies = this.moviesRepository.findByName("xaxa");

        Assertions.assertThat(movies).isEmpty();
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is empty")
    void save_ThrowsConstraintViolationException_WhenNameIsEmpty(){
        Movies movie = new Movies();

        //Assertions.assertThatThrownBy(() -> this.moviesRepository.save(movie))
        //        .isInstanceOf(ConstraintViolationException.class);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.moviesRepository.save(movie))
                .withMessageContaining("The movie name cannot be empty");
    }

}
