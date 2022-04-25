package academy.lima.catalog.util;

import academy.lima.catalog.domain.dto.MoviesDto;

public class MovieDtoCreator {

    public static MoviesDto createMovieDtoToBeSaved(){
        return MoviesDto.builder()
                .name("Harry Potter")
                .build();
    }

    public static MoviesDto createValidMovieDto(){
        return MoviesDto.builder()
                .name("Harry Potter")
                .id(1L)
                .build();
    }

    public static MoviesDto createValidUpdatedMovieDto(){
        return MoviesDto.builder()
                .name("Harry Potter 2")
                .id(1L)
                .build();
    }
}
