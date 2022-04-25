package academy.lima.catalog.service;

import academy.lima.catalog.controller.MoviesController;
import academy.lima.catalog.domain.Movies;
import academy.lima.catalog.repository.MoviesRepository;
import academy.lima.catalog.util.MovieCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class MoviesServiceTest {

    @InjectMocks
    private MoviesService moviesService;

    @Mock
    private MoviesRepository moviesRepository;

    @BeforeEach
    void setUp(){
        PageImpl<Movies> movie = new PageImpl<>(List.of(MovieCreator.createValidMovie()));
        BDDMockito.when(moviesRepository.findAll((Pageable) ArgumentMatchers.any()))
                .thenReturn(movie);

        BDDMockito.when(moviesRepository.findAll())
                .thenReturn(List.of(MovieCreator.createValidMovie()));

        BDDMockito.when(moviesRepository.getById(1L))
                .thenReturn(MovieCreator.createValidMovie());

        BDDMockito.when(moviesRepository.findByName("Harry Potter"))
                .thenReturn(List.of(MovieCreator.createValidMovie()));

        BDDMockito.when(moviesRepository.save(MovieCreator.createMovieToBeSaved()))
                .thenReturn(MovieCreator.createValidMovie());

        BDDMockito.doNothing()
                .when(moviesRepository).delete(MovieCreator.createValidMovie());

    }

    @Test
    @DisplayName("list returns list of movie inside page object when successful")
    void list_ReturnsListOfMoviesInsidePageObject_WhenSuccessful(){

        String expectedName = MovieCreator.createValidMovie().getName();

        Page<Movies> moviesPage = moviesService.listPage(null);

        Assertions.assertThat(moviesPage).isNotNull();

        Assertions.assertThat(moviesPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(moviesPage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAll returns list of movie when successful")
    void listAll_ReturnsListOfMovies_WhenSuccessful(){

        String expectedName = MovieCreator.createValidMovie().getName();

        List<Movies> movies = moviesRepository.findAll();

        Assertions.assertThat(movies).isNotNull();

        Assertions.assertThat(movies)
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(movies.get(0).getName()).isEqualTo(expectedName);

    }

    @Test
    @DisplayName("findById returns movie when successful")
    void findById_ReturnsMovie_WhenSuccessful(){

        Movies validMovie = MovieCreator.createValidMovie();

        Movies movie = moviesRepository.getById(1L);

        Assertions.assertThat(movie)
                .isNotNull();

        Assertions.assertThat(movie.getName()).isEqualTo(validMovie.getName());
        Assertions.assertThat(movie.getId()).isEqualTo(validMovie.getId());
    }

    @Test
    @DisplayName("findByName returns a list of movie when successful")
    void findByName_ReturnsListOfMovie_WhenSuccessful(){

        Movies validMovie = MovieCreator.createValidMovie();

        List<Movies> movie = moviesRepository.findByName("Harry Potter");

        Assertions.assertThat(movie)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(movie.get(0).getName()).isEqualTo(validMovie.getName());
        Assertions.assertThat(movie.get(0).getId()).isEqualTo(validMovie.getId());

    }

    @Test  // Error in debug
    @DisplayName("findByName returns an empty list of movie when movie is not found")
    void findByName_ReturnsEmptyListOfMovie_WhenMovieIsNotFound() {

        BDDMockito.when(moviesRepository.findByName("Harry Potter"))
                .thenReturn(List.of());

        List<Movies> movie = moviesRepository.findByName("Harry Potter");

        Assertions.assertThat(movie)
                .isEmpty();
    }

    @Test
    @DisplayName("save returns movie when successful")
    void save_ReturnsMovie_WhenSuccessful(){

        Movies movieToBeSaved = MovieCreator.createMovieToBeSaved();

        Movies saved = moviesRepository.save(movieToBeSaved);

        Assertions.assertThat(saved)
                .isNotNull();

        Assertions.assertThat(saved.getId())
                .isNotNull();

        Assertions.assertThat(saved.getName())
                .isEqualTo(movieToBeSaved.getName());
    }

  @Test
    @DisplayName("delete movie when successful")
    void delete_RemovesMovie_WhenSuccessful(){
      moviesRepository.delete(MovieCreator.createValidMovie());
      BDDMockito.verify(moviesRepository,BDDMockito.times(1)).delete(MovieCreator.createValidMovie());

    }

}