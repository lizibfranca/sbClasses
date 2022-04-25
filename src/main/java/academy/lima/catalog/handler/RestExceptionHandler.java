package academy.lima.catalog.handler;

import academy.lima.catalog.exception.BadRequestException;
import academy.lima.catalog.exception.BadRequestExceptionDetails;
import academy.lima.catalog.exception.ExceptionDetails;
import academy.lima.catalog.exception.ValidationExceptionDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException e){
        return new ResponseEntity<>(
                BadRequestExceptionDetails
                        .builder()
                        .title("Bad Request Exception")
                        .details(e.getMessage())
                        .developerMessage(e.getClass().getName())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FieldError> fieldErrors = e.getBindingResult()
                                            .getFieldErrors();
        String fields = fieldErrors
                                .stream()
                                .map(FieldError::getField)
                                .collect(Collectors.joining(","));

        String fieldsMessage = fieldErrors
                                    .stream()
                                    .map(FieldError::getDefaultMessage)
                                    .collect(Collectors.joining(","));

        return new ResponseEntity<>(
                ValidationExceptionDetails
                        .builder()
                        .title("Validation Exception: Invalid Fields")
                        .fields(fields)
                        .fieldsMessage(fieldsMessage)
                        .details(e.getMessage())
                        .developerMessage(e.getClass().getName())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, 0);
        }

        ExceptionDetails exceptionDetails = ExceptionDetails
                .builder()
                .title(ex.getCause().getMessage())
                .details(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity(exceptionDetails, headers, status);
    }


}
