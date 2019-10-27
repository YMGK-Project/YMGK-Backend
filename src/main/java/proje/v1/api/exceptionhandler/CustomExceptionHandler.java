package proje.v1.api.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import proje.v1.api.base.messages.Response;
import proje.v1.api.exceptionhandler.base.BadRequestExcepiton;
import proje.v1.api.exceptionhandler.base.ForbiddenException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Response<String>> forbiddenException(Exception exception){
        Response<String> response = new Response<>(403, false, exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadRequestExcepiton.class)
    public ResponseEntity<Response<String>> badRequestException(Exception exception){
        Response<String> response = new Response<>(400, false, exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
