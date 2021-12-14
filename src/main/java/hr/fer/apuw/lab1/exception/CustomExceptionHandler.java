package hr.fer.apuw.lab1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NoSuchEntityException.class)
    protected ResponseEntity<?> handleNoSuchEntityException(Exception exception, WebRequest webRequest){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
