package academy.lima.catalog.util;

import academy.lima.catalog.domain.Movies;

public class MovieCreator {

    public static Movies createMovieToBeSaved(){
        return Movies.builder()
                .name("Harry Potter")
                .build();
    }

    public static Movies createValidMovie(){
        return Movies.builder()
                .name("Harry Potter")
                .id(1L)
                .build();
    }

    public static Movies createValidUpdatedMovie(){
        return Movies.builder()
                .name("Harry Potter 2")
                .id(1L)
                .build();
    }
}
