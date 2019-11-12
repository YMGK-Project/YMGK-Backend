package proje.v1.api.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import proje.v1.api.common.messages.Response;
import proje.v1.api.exception.*;

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

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response<String>> notFoundException(Exception exception){
        Response<String> response = new Response<>(404,false,exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnhandledException.class)
    public ResponseEntity<Response<String>> unhandledException(Exception exception){
        Response<String> response = new Response<>(500, false, exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(500));
    }

    @ExceptionHandler(UnAuthenticationException.class)
    public ResponseEntity<Response<String>> unAuthenticationException(Exception exception){
        Response<String> response = new Response<>(401, false, exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
