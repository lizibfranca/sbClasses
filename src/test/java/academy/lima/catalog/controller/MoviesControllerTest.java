package academy.lima.catalog.controller;

import academy.lima.catalog.domain.Movies;
import academy.lima.catalog.service.MoviesService;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

//@SpringBootTest
@ExtendWith(SpringExtension.class)
class MoviesControllerTest {

    @InjectMocks
    private MoviesController moviesController;

    @Mock
    private MoviesService moviesServiceMock;

    @BeforeEach
    void setUp(){
        PageImpl<Movies> movie = new PageImpl<>(List.of(MovieCreator.createValidMovie()));
        BDDMockito.when(moviesServiceMock.listPage(ArgumentMatchers.any()))
                .thenReturn(movie);

        BDDMockito.when(moviesServiceMock.listAll())
                .thenReturn(List.of(MovieCreator.createValidMovie()));

        BDDMockito.when(moviesServiceMock.getMovieById(1L))
                .thenReturn(MovieCreator.createValidMovie());

        BDDMockito.when(moviesServiceMock.getMovieByName("Harry Potter"))
                .thenReturn(List.of(MovieCreator.createValidMovie()));

        BDDMockito.when(moviesServiceMock.save(MovieCreator.createMovieToBeSaved()))
                .thenReturn(MovieCreator.createValidMovie());

        BDDMockito.doNothing()
                .when(moviesServiceMock).update(ArgumentMatchers.anyLong(), ArgumentMatchers.any(Movies.class));


        BDDMockito.doNothing()
                .when(moviesServiceMock).delete(ArgumentMatchers.anyLong());

    }

    @Test
    @DisplayName("list returns list of movie inside page object when successful")
    void list_ReturnsListOfMoviesInsidePageObject_WhenSuccessful(){

        String expectedName = MovieCreator.createValidMovie().getName();

        Page<Movies> moviesPage = moviesController.listPage(null).getBody();

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

        List<Movies> movies = moviesController.listAll().getBody();

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

        Movies movie = moviesController.getById(1L).getBody();

        Assertions.assertThat(movie)
                .isNotNull();

        Assertions.assertThat(movie.getName()).isEqualTo(validMovie.getName());
        Assertions.assertThat(movie.getId()).isEqualTo(validMovie.getId());
    }

    @Test
    @DisplayName("findByName returns a list of movie when successful")
    void findByName_ReturnsListOfMovie_WhenSuccessful(){

        Movies validMovie = MovieCreator.createValidMovie();

        List<Movies> movie = moviesController.getByName("Harry Potter").getBody();

        Assertions.assertThat(movie)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(movie.get(0).getName()).isEqualTo(validMovie.getName());
        Assertions.assertThat(movie.get(0).getId()).isEqualTo(validMovie.getId());
        
    }

    @Test
    @DisplayName("findByName returns an empty list of movie when movie is not found")
    void findByName_ReturnsEmptyListOfMovie_WhenMovieIsNotFound() {

        BDDMockito.when(moviesServiceMock.getMovieByName("Harry Potter"))
                .thenReturn(List.of());

        List<Movies> movie = moviesController.getByName("Harry Potter").getBody();

        Assertions.assertThat(movie)
                .isEmpty();
    }

    @Test
    @DisplayName("save returns movie when successful")
    void save_ReturnsMovie_WhenSuccessful(){

        Movies movieToBeSaved = MovieCreator.createMovieToBeSaved();

        Movies saved = moviesController.create(movieToBeSaved).getBody();

        Assertions.assertThat(saved)
                .isNotNull();

        Assertions.assertThat(saved.getId())
                .isNotNull();

        Assertions.assertThat(saved.getName())
                .isEqualTo(movieToBeSaved.getName());
    }

    @Test
    @DisplayName("replace updates movie when successful")
    void replace_UpdatesMovie_WhenSuccessful(){

        Movies movie = MovieCreator.createValidUpdatedMovie();

        ResponseEntity<Void> voidResponseEntity = moviesController.update(movie.getId(), movie);

        Assertions.assertThat(voidResponseEntity)
                .isNotNull();

        Assertions.assertThat(voidResponseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    @DisplayName("delete removes movie when successful")
    void delete_RemovesMovie_WhenSuccessful(){

        ResponseEntity<Void> voidResponseEntity = moviesController.delete(1L);

        Assertions.assertThat(voidResponseEntity)
                .isNotNull();

        Assertions.assertThat(voidResponseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);


        
    }

}
