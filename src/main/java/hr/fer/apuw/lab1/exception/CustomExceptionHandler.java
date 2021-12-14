package hr.fer.apuw.lab1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NoSuchEntityException.class)
    protected ResponseEntity<?> handleNoSuchEntityException(Exception exception, WebRequest webRequest){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    protected ResponseEntity<?> handleForbiddenAccessException(Exception exception, WebRequest webRequest){
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    protected ResponseEntity<?> handleEntityAlreadyExistsException(Exception exception, WebRequest webRequest){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
