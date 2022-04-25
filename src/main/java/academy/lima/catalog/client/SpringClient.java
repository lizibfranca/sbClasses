package academy.lima.catalog.client;


import academy.lima.catalog.domain.Movies;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {

    public static void main(String[] args) {
        /*ResponseEntity<Movies> entity = new RestTemplate().getForEntity("http://localhost:3000/movies/1", Movies.class);
        log.info(entity);


        Movies movie = new RestTemplate().getForEntity("http://localhost:3000/movies/1", Movies.class).getBody();
        log.info(movie);

        Movies object = new RestTemplate().getForObject("http://localhost:3000/movies/{id}", Movies.class, 1);
        log.info(object);

        Movies[] objectList = new RestTemplate().getForObject("http://localhost:3000/movies/all", Movies[].class);
        log.info(Arrays.toString(objectList));
*/

/*
        ResponseEntity<List<Movies>> exchange = new RestTemplate().exchange("http://localhost:3000/movies/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Movies>>() {
                });
        log.info(exchange.getBody());
*/
/*
        Movies doFuturo = Movies.builder().name("Exterminador do Futuro").uuid("asd").build();
        Movies doFuturoSaved = new RestTemplate().postForObject("http://localhost:3000/movies/", doFuturo, Movies.class);
        log.info(doFuturoSaved);

        Movies doFuturo1 = Movies.builder().name("Exterminador do Futuro 1").build();
        ResponseEntity<Movies> doFuturo1Saved = new RestTemplate().exchange("http://localhost:3000/movies/",
                HttpMethod.POST,
                new HttpEntity<>(doFuturo1, createJsonHeader()),
                Movies.class);
        log.info(doFuturo1Saved);

        Movies futuro1Saved = doFuturo1Saved.getBody();
        futuro1Saved.setName("Teste");
        new RestTemplate().exchange("http://localhost:3000/movies/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(futuro1Saved, createJsonHeader()),
                Void.class,
                futuro1Saved.getId());

        log.info(futuro1Saved);

        ResponseEntity<Void> exchange1 = new RestTemplate().exchange("http://localhost:3000/movies/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                futuro1Saved.getId());

        log.info(exchange1);
*/
    }

    private static HttpHeaders createJsonHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;

    }
}
