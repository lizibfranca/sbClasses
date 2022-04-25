package academy.lima.catalog.controller;

import academy.lima.catalog.domain.Movies;
import academy.lima.catalog.domain.dto.MoviesDto;
import academy.lima.catalog.service.MoviesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("movies")
@RequiredArgsConstructor
public class MoviesController {

    private final MoviesService moviesService;

    @GetMapping(path = "page")
    public ResponseEntity<Page<Movies>> listPage(Pageable pageable) {
        return ResponseEntity.ok(moviesService.listPage(pageable));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Movies>> listAll() {
        return ResponseEntity.ok(moviesService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movies> getById(@PathVariable long id) {
        return new ResponseEntity<>(moviesService.getMovieById(id), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<List<Movies>> getByName(@RequestParam String name) {
        return new ResponseEntity<>(moviesService.getMovieByName(name), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Movies> create(@RequestBody @Valid Movies movie) {
        return new ResponseEntity<>(moviesService.save(movie), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Movies movie){
        moviesService.update(id, movie);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        moviesService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
