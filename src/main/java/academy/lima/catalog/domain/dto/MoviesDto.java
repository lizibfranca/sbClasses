package academy.lima.catalog.domain.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class MoviesDto {

    private Long id;

    @NotBlank(message = "Field NAME cannot be null or empty")
    private String name;

}
