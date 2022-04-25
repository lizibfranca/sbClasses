package academy.lima.catalog.mapper;

import academy.lima.catalog.domain.Movies;
import academy.lima.catalog.domain.dto.MoviesDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MoviesMapper {

    MoviesMapper INSTANCE = Mappers.getMapper(MoviesMapper.class);

    MoviesDto movieToMoviesDto(Movies movies);

    List<MoviesDto> movieListToMoviesDtoList(List<Movies> movies);

    Movies movieDtoToMovie(MoviesDto moviesDto);
}
