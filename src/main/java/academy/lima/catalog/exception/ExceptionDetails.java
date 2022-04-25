package academy.lima.catalog.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDetails {

    private String title;
    private String details;
    private String developerMessage;
    private int status;
    private LocalDateTime timestamp;

}
