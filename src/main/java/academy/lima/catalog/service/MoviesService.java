package academy.lima.catalog.service;

import academy.lima.catalog.domain.Movies;
import academy.lima.catalog.domain.dto.MoviesDto;
import academy.lima.catalog.exception.BadRequestException;
import academy.lima.catalog.mapper.MoviesMapper;
import academy.lima.catalog.repository.MoviesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MoviesService {

    private final MoviesRepository moviesRepository;

    public Page<Movies> listPage(Pageable pageable){
        return moviesRepository.findAll(pageable);
    }

    public List<Movies> listAll(){
        return moviesRepository.findAll();
    }

    public Movies getMovieById(long id) {
        return moviesRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException("Movie not found"));

    }

    @Transactional
    public Movies save(Movies movie) {
        return moviesRepository.save(movie);
    }

    @Transactional
    public void update(Long id, Movies dto) {
        moviesRepository.findById(id);
        moviesRepository.save(dto);
    }

    public void delete(Long id) {
        moviesRepository.findById(id);
        moviesRepository.deleteById(id);
    }

    public List<Movies> getMovieByName(String name) {
        return moviesRepository.findByName(name);
    }
}
